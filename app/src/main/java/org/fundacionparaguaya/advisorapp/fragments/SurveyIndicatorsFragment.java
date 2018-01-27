package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.NonSwipeableViewPager;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;


/**
 * Created by alex on 1/23/2018.
 */

public class SurveyIndicatorsFragment extends AbstractSurveyFragment{

    IndicatorAdapter mAdapter;
    NonSwipeableViewPager mPager;

    LinearLayout backButton;
    TextView backButtonText;
    LinearLayout skipButton;
    TextView skipButtonText;

    @Inject
    InjectionViewModelFactory mViewModelFactory;

    SharedSurveyViewModel mSurveyViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInsanceState) {
        super.onCreate(savedInsanceState);
        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        setFooterColor(R.color.survey_grey);
        setHeaderColor(R.color.survey_grey);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_surveyindicatorsurvey, container, false);

        mAdapter = new IndicatorAdapter(getFragmentManager(), mSurveyViewModel, this);
        mPager = (NonSwipeableViewPager) view.findViewById(R.id.indicatorsurvey_viewpager);

        mPager.setAdapter(mAdapter);

        backButton = (LinearLayout) view.findViewById(R.id.indicatorsurvey_backbutton);
        backButtonText = (TextView) view.findViewById(R.id.indicatorsurvey_backbuttontext);
        skipButton = (LinearLayout) view.findViewById(R.id.indicatorsurvey_skipbutton);
        skipButtonText = (TextView) view.findViewById(R.id.indicatorsurvey_skipbuttontext);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() == mAdapter.getCount() - 1){
                    //TODO: Close the survey activity

                } else {
                    mSurveyViewModel.addSkippedIndicator(mAdapter.getQuestion(mPager.getCurrentItem()));
                    nextQuestion();
                }
            }
        });
        return view;
    }

    public void nextQuestion(){
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
        checkConditions();
    }

    public void previousQuestion(){
        if (mPager.getCurrentItem() < 1) {
            //Goes back when on the first survey question
            //mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.BACKGROUND_QUESTIONS);
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            checkConditions();
        }
    }

    public IndicatorOption getResponses(IndicatorQuestion question){
        return mSurveyViewModel.getResponseForIndicator(question);
    }

    public void addIndicatorResponse(IndicatorQuestion question, IndicatorOption option){
        mSurveyViewModel.addIndicatorResponse(question, option);
    }

    public void addSkippedIndicator(IndicatorQuestion question) {
        mSurveyViewModel.addSkippedIndicator(question);
    }

    private void checkConditions(){
        if (mPager.getCurrentItem() == mAdapter.getCount()-1) {
            backButton.setVisibility(getView().VISIBLE);
            skipButton.setVisibility(getView().VISIBLE);
            skipButtonText.setText(R.string.survey_finish);
        } else if (mPager.getCurrentItem() == 0){
            skipButtonText.setText(R.string.survey_skip);
            backButton.setVisibility(getView().VISIBLE); //change to gone when you get the selecting to work
            skipButton.setVisibility(getView().VISIBLE);
        } else if (mPager.getCurrentItem() == mAdapter.getCount() - 2) {
            skipButtonText.setText(R.string.survey_skip);
            backButton.setVisibility(getView().VISIBLE);
            skipButton.setVisibility(getView().VISIBLE);
        } else {
            skipButtonText.setText(R.string.survey_skip);
            backButton.setVisibility(getView().VISIBLE);
            skipButton.setVisibility(getView().VISIBLE);
        }
    }

    public static SurveyIndicatorsFragment build(){
        SurveyIndicatorsFragment fragment = new SurveyIndicatorsFragment();
        return fragment;
    }


}
