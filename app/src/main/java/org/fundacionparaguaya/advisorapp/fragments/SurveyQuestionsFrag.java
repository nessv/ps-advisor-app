package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.BackgroundQuestionAdapter;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.BackgroundQuestionCallback;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Questions about Personal and Economic questions that are asked before the survey
 */

public class SurveyQuestionsFrag extends AbstractSurveyFragment implements BackgroundQuestionCallback {

    static String FRAGMENT_TAG = "SurveyQuestionsFrag";

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSharedSurveyViewModel;

    RecyclerView mDsvQuestionList;
    BackgroundQuestionAdapter mAdapter;

    public SurveyQuestionsFrag()
    {
        super();

        //sets colors for parent activity (set by parent activity in SurveyActivity.switchSurveyFrag)
        setFooterColor(R.color.survey_darkyellow);
        setHeaderColor(R.color.survey_darkyellow);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSharedSurveyViewModel = ViewModelProviders.
                 of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        setTitle("Background Questions");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Survey survey = mSharedSurveyViewModel.getSurveyInProgress();
        List<BackgroundQuestion> questions = new ArrayList<>(
                survey.getPersonalQuestions().size() + survey.getEconomicQuestions().size());
        questions.addAll(survey.getPersonalQuestions());
        questions.addAll(survey.getEconomicQuestions());

        mAdapter.setQuestionsList(questions);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_surveyquestions, container, false);

        mDsvQuestionList = v.findViewById(R.id.dsv_surveyquestions_list);
        mAdapter = new BackgroundQuestionAdapter(this);
        mDsvQuestionList.setAdapter(mAdapter);
        //mDsvQuestionList.setItemTransformer(new BackgroundQuestionAdapter.QuestionFadeTransformer());

        mDsvQuestionList.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onQuestionAnswered(BackgroundQuestion q, Object response) {
        try {
            //all responses to questions (for now) should be strings
            mSharedSurveyViewModel.addBackgroundResponse(q, (String)response);
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void onNext(View v) {
       /* int currentIndex = mDsvQuestionList.getCurrentItem();
        currentIndex++;

        if(currentIndex<mAdapter.getItemCount())
        {
            mDsvQuestionList.smoothScrollToPosition(currentIndex);
        }*/
    }

    @Override
    public void onFinish() {
        //should check if all required questions have been answered before transitioning
        mSharedSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.INDICATORS);
    }
}
