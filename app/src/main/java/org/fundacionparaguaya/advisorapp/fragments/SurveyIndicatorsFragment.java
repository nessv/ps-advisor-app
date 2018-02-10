package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
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
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.NonSwipeableViewPager;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.Set;

import javax.inject.Inject;


/**
 * Enables user to go through each of the indicators, skip indicators, select a red, yellow, green color etc.
 */

public class SurveyIndicatorsFragment extends AbstractSurveyFragment implements ViewPager.OnPageChangeListener {

    IndicatorAdapter mAdapter;
    NonSwipeableViewPager mPager;

    LinearLayout backButton;
    TextView backButtonText;
    LinearLayout skipButton;
    TextView skipButtonText;

    private boolean isPageChanged = true;

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
        setTitle(getString(R.string.survey_indicators_title));

        setShowHeader(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveyindicators, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        mAdapter = new IndicatorAdapter(getChildFragmentManager(), mSurveyViewModel, this);
        mPager = (NonSwipeableViewPager) view.findViewById(R.id.indicatorsurvey_viewpager);

        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);

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
                if (!mAdapter.getIndicatorFragment(mPager.getCurrentItem()).isCardSelected()) {
                    addSkippedIndicator(mAdapter.getQuestion(mPager.getCurrentItem()));
                }
                nextQuestion();
            }
        });
        checkConditions();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void nextQuestion() {
        if (isPageChanged) {
            if (mPager.getCurrentItem() == mAdapter.getCount() - 1) {
                mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.SUMMARY);
            } else {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                checkConditions();
            }
        }
    }

    public void previousQuestion() {
        if (isPageChanged) {
            if (mPager.getCurrentItem() < 1) {
                //Goes back when on the first survey question
                mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.ECONOMIC_QUESTIONS);
            } else {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                checkConditions();
            }
        }
    }

    public IndicatorOption getResponses(IndicatorQuestion question) {
        return mSurveyViewModel.getResponseForIndicator(question);
    }

    public void addIndicatorResponse(IndicatorQuestion question, IndicatorOption option) {
        mSurveyViewModel.addIndicatorResponse(question, option);
    }

    public void addSkippedIndicator(IndicatorQuestion question) {
        mSurveyViewModel.addSkippedIndicator(question);
    }

    public Set<IndicatorQuestion> getSkippedIndicators() {
        return mSurveyViewModel.getSkippedIndicators();
    }

    public void removeIndicatorResponse(IndicatorQuestion question) {
        mSurveyViewModel.removeIndicatorResponse(question);
    }

    private void checkConditions() {
        if (mPager.getCurrentItem() == 0) {
            skipButtonText.setText(R.string.survey_skip);
        } else if (mAdapter.getIndicatorFragment(mPager.getCurrentItem()).isCardSelected()) {
            skipButtonText.setText(R.string.survey_next);
        } else {
            skipButtonText.setText(R.string.survey_skip);
        }
    }

    public static SurveyIndicatorsFragment build() {
        SurveyIndicatorsFragment fragment = new SurveyIndicatorsFragment();
        return fragment;
    }

    public boolean isPageChanged() {
        return isPageChanged;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //For future implementation
    }

    @Override
    public void onPageSelected(int position) {
        //For future implementation
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                isPageChanged = true;
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                isPageChanged = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isPageChanged = false;
                break;
            default:
                isPageChanged = false;
                break;
        }

    }
}
