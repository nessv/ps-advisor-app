package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;
import org.w3c.dom.Text;

import javax.inject.Inject;

import static org.fundacionparaguaya.advisorapp.fragments.IndicatorFragment.SelectedIndicator.NONE;

/**
 * Created by alex on 1/23/2018.
 */

public class IndicatorSurveyFragment extends AbstractSurveyFragment{

    IndicatorAdapter mAdapter;
    ViewPager mPager;

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_surveyindicatorsurvey, container, false);

        mAdapter = new IndicatorAdapter(getFragmentManager(), mSurveyViewModel);
        mPager = (ViewPager) view.findViewById(R.id.indicatorsurvey_viewpager);

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
                mSurveyViewModel.addSkippedIndicator(mAdapter.getQuestion(mPager.getCurrentItem()));
                nextQuestion();
            }
        });

        return view;
    }

    public void nextQuestion(){
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    public void previousQuestion(){
        mPager.setCurrentItem(mPager.getCurrentItem()-1);
    }

    public static IndicatorSurveyFragment build(){
        IndicatorSurveyFragment fragment = new IndicatorSurveyFragment();
        return fragment;
    }


}
