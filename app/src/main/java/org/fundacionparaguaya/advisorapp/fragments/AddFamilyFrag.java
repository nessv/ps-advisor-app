package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.AddFamilyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class AddFamilyFrag extends SurveyQuestionsFrag {

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
        setTitle("New Family");
    }

    @Override
    protected void initQuestionList() {
        mAddFamilyViewModel.getQuestions().observe(this,
            mQuestionAdapter::setQuestionsList);
    }

    @Override
    public void onQuestionAnswered(BackgroundQuestion q, Object response) {
        mAddFamilyViewModel.addFamilyResponse(q, response);
    }

    @Override
    public void onFinish() {
        new SaveFamilyAsyncTask(this).execute();
        //set family in survey view model..
        //change state
    }

    static class SaveFamilyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private WeakReference<AddFamilyFrag> fragReference;

        SaveFamilyAsyncTask(AddFamilyFrag frag)
        {
            fragReference = new WeakReference<AddFamilyFrag>(frag);
        }

        @Override
        protected Void doInBackground (Void ... voids) {
            fragReference.get().mAddFamilyViewModel.saveFamily();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
       //     fragReference.get().navigateBack();

            //CHANGE STATE HERE

            super.onPostExecute(aVoid);
        }
    }
}
