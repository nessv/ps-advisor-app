package org.fundacionparaguaya.advisorapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.IndicatorSurveyFragment;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel.*;

import javax.inject.Inject;

/**
 * Activity for surveying a family's situation. Displays the fragments that record background info and allows
 * the family to select indicators
 */

public class SurveyActivity extends AbstractFragSwitcherActivity
{
    static String FAMILY_ID_KEY = "FAMILY_ID";

    SurveyIntroFragment introFragment;

    IndicatorSurveyFragment indicatorSurveyFragment;

    LinearLayout header;
    LinearLayout fragmentContainer;
    RelativeLayout footer;


    @Inject
    InjectionViewModelFactory mViewModelFactory;

    SharedSurveyViewModel mSurveyViewModel;

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

        header = (LinearLayout) findViewById(R.id.survey_activity_header);
        fragmentContainer = (LinearLayout) findViewById(R.id.survey_activity_fragment_container);
        footer = (RelativeLayout) findViewById(R.id.survey_activity_footer);

        /**Construct fragments here**/
        introFragment = SurveyIntroFragment.build();
        indicatorSurveyFragment = IndicatorSurveyFragment.build();

        /** Add all fragments you want to switch between as parameter here**/

        initFragSwitcher(R.id.survey_activity_fragment_container, introFragment, indicatorSurveyFragment);

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
                    header.setBackgroundColor(this.getColor(R.color.survey_lightyellow));
                    fragmentContainer.setBackgroundColor(this.getColor(R.color.survey_lightyellow));
                    footer.setBackgroundColor(this.getColor(R.color.survey_darkyellow));
                    switchToFrag(introFragment);
                    break;

                case BACKGROUND_QUESTIONS:
                    header.setBackgroundColor(this.getColor(R.color.survey_darkyellow));
                    fragmentContainer.setBackgroundColor(this.getColor(R.color.survey_lightyellow));
                    footer.setBackgroundColor(this.getColor(R.color.survey_darkyellow));
                    //switchToFrag( );
                    //break;

                case INDICATORS:
                    header.setBackgroundColor(this.getColor(R.color.survey_whitebackground));
                    fragmentContainer.setBackgroundColor(this.getColor(R.color.survey_whitebackground));
                    footer.setBackgroundColor(this.getColor(R.color.survey_grey));
                    switchToFrag(indicatorSurveyFragment);
                    break;
            };
        });
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