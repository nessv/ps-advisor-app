package org.fundacionparaguaya.advisorapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.AbstractSurveyFragment;
import org.fundacionparaguaya.advisorapp.fragments.BackgroundQuestionsFrag;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel.*;
import org.w3c.dom.Text;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

/**
 * Activity for surveying a family's situation. Displays the fragments that record background info and allows
 * the family to select indicators
 */

public class SurveyActivity extends AbstractFragSwitcherActivity
{
    static String FAMILY_ID_KEY = "FAMILY_ID";

    SurveyIntroFragment mIntroFragment;
    BackgroundQuestionsFrag mQuestionsFragment;

    TextView mTvTitle;

    @Inject
    InjectionViewModelFactory mViewModelFactory;

    SharedSurveyViewModel mSurveyViewModel;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("init", true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        ((AdvisorApplication) this.getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(this, mViewModelFactory)
                .get(SharedSurveyViewModel.class);


        mTvTitle = findViewById(R.id.tv_surveyactivity_title);

        /**Construct fragments here**/
        mIntroFragment = SurveyIntroFragment.build();
        mQuestionsFragment = new BackgroundQuestionsFrag();


        setFragmentContainer(R.id.survey_activity_fragment_container);
        /** Add all fragments you want to switch between as parameter here**/

        initViewModel();
    }


    public void initViewModel()
    {
        //familyId can never equal -1 if retrieved from the database, so it is used as the default value
        int familyId = getIntent().getIntExtra(FAMILY_ID_KEY, -1);

        if(familyId == -1)
        {
            throw new IllegalArgumentException(this.getLocalClassName() + ": Found family id of -1. Family id is either not set" +
                    "or has been set innappropriately. To launch this activity with the family id properly set, use the " +
                    "build(int) function");
        }

        mSurveyViewModel.setFamily(familyId);

        //observe changes for family, when it has a value then show intro.
        mSurveyViewModel.getCurrentFamily().observe(this, (family ->
        {
            if(mSurveyViewModel.getSurveyState().getValue().equals(SurveyState.NONE))
            {
                mSurveyViewModel.getSurveyState().setValue(SurveyState.INTRO);
            }
        }));

        mSurveyViewModel.getSnapshot().observe(this, snapshot -> {
            //update progress bar
        });

        mSurveyViewModel.getSurveyState().observe(this, surveyState -> {
            switch (surveyState)
            {
                case INTRO:

                    if(!hasFragForClass(SurveyIntroFragment.class))
                    {
                        addFragment(mIntroFragment);
                    }

                    getSupportFragmentManager().executePendingTransactions();

                    switchToFrag(SurveyIntroFragment.class);

                    break;

                case BACKGROUND_QUESTIONS:

                    if(!hasFragForClass(BackgroundQuestionsFrag.class))
                    {
                        addFragment(mQuestionsFragment);
                    }

                    getSupportFragmentManager().executePendingTransactions();

                    switchToFrag(BackgroundQuestionsFrag.class);

                    break;

              //  case INDICATORS:

                /* * etc * */
            };
        });
    }

    @Override
    public void switchToFrag(Class fragmentClass)
    {
        if(!hasFragForClass(fragmentClass))
        {
            try{
                Fragment f = (Fragment)fragmentClass.getConstructor().newInstance();
                addFragment(f);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        AbstractSurveyFragment fragment = (AbstractSurveyFragment)getFragment(fragmentClass);
        this.mTvTitle.setText(fragment.getTitle());

        super.switchToFrag(fragmentClass);
    }

    public void setTitle(String title) {

    }

    //Returns and intent to open this activity, with an extra for the family's Id.
    public static Intent build(Context c, Family family)
    {
        Intent intent = new Intent(c, SurveyActivity.class);
        intent.putExtra(FAMILY_ID_KEY, family.getId());

        return intent;
    }
}
