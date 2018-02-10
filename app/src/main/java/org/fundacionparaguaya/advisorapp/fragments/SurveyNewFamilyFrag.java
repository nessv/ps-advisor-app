package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.viewmodels.AddFamilyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;
import java.lang.ref.WeakReference;

public class SurveyNewFamilyFrag extends SurveyQuestionsFrag {

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSurveyViewModel;

    AddFamilyViewModel mAddFamilyViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        mAddFamilyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(AddFamilyViewModel.class);

        setFooterColor(R.color.survey_grey);
        setHeaderColor(R.color.survey_grey);

        setTitle(getString(R.string.addfamily_new_family_title));
    }

    @Override
    protected void initQuestionList() {
        mSurveyViewModel.getSurveys().observe(this, (surveys) ->
        {
            if (surveys!=null && surveys.size() > 0) {
                Survey survey = surveys.get(0);

                mSurveyViewModel.makeSnapshot(survey); //assumes family livedata object has value

                mQuestionAdapter.setQuestionsList(mSurveyViewModel.getSurveyInProgress().getPersonalQuestions());
            }
        });
    }

    @Override
    public void onQuestionAnswered(BackgroundQuestion q, Object response) {
        mAddFamilyViewModel.addFamilyResponse(q, response);
    }

    @Override
    public void onSubmit() {
       mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.ECONOMIC_QUESTIONS);
        //set family in survey view model..
        //change state
    }

    @Override
    public String getResponseFor(BackgroundQuestion q) {
       return mSharedSurveyViewModel.getBackgroundResponse(q);
    }
}
