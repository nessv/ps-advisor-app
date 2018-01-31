package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import org.fundacionparaguaya.advisorapp.viewcomponents.IndicatorCard;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;

/**
 *
 */

public class SurveySummaryIndicatorsFragment extends AbstractSurveyFragment implements View.OnClickListener{

    IndicatorCard mGreenCard;
    IndicatorCard mYellowCard;
    IndicatorCard mRedCard;

    LinearLayout backButton;
    TextView backButtonText;
    LinearLayout saveButton;
    TextView saveButtonText;

    IndicatorQuestion question;

    SurveySummaryFragment parentFragment;

    SharedSurveyViewModel mSurveyViewModel;

    @Inject
    InjectionViewModelFactory mViewModelFactory;

    @Nullable
    IndicatorCard selectedIndicatorCard;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        setFooterColor(R.color.surveysummary_background);
        setHeaderColor(R.color.surveysummary_background);
        setShowFooter(false);
        setTitle(getString(R.string.survey_summary_title));
    }

    @Override
    public void onResume() {
        mGreenCard = (IndicatorCard) getView().findViewById(R.id.surveysummary_indicatorcard_green);
        mYellowCard = (IndicatorCard) getView().findViewById(R.id.surveysummary_indicatorcard_yellow);
        mRedCard = (IndicatorCard) getView().findViewById(R.id.surveysummary_indicatorcard_red);

        backButton = (LinearLayout) getView().findViewById(R.id.surveysummary_indicator_backbutton);
        backButtonText = (TextView) getView().findViewById(R.id.surveysummary_indicator_backbuttontext);

        saveButton = (LinearLayout) getView().findViewById(R.id.surveysummary_indicator_nextbutton);
        saveButtonText = (TextView) getView().findViewById(R.id.surveysummary_indicator_nextbuttontext);

        question = mSurveyViewModel.getFocusedQuestion();

        for (IndicatorOption option : question.getOptions()) {
            switch (option.getLevel()) {
                case Green:
                    mGreenCard.setOption(option);
                    break;
                case Yellow:
                    mYellowCard.setOption(option);
                    break;
                case Red:
                    mRedCard.setOption(option);
                    break;
            }
        }

        IndicatorOption existingResponse = mSurveyViewModel.getResponseForIndicator(question);

        if(existingResponse!=null)
        {
            switch (existingResponse.getLevel())
            {
                case Green:
                    mGreenCard.setSelected(true);
                    break;

                case Yellow:
                    mYellowCard.setSelected(true);
                    break;

                case Red:
                    mRedCard.setSelected(true);
                    break;
            }
        }

        mGreenCard.setOnClickListener(this);
        mYellowCard.setOnClickListener(this);
        mRedCard.setOnClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.SUMMARY);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mSurveyViewModel.addIndicatorResponse(question, selectedIndicatorCard.getOption());
                } catch (NullPointerException e) {
                    mSurveyViewModel.addSkippedIndicator(question);
                }
                mSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.SUMMARY);
            }
        });
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_surveysummary_chooseindicator, container, false);

        return rootView;
    }

    /**
     * When one of the cards is selected...
     * @param view IndicatorCard
     */
    @Override
    public void onClick(View view) {
        if(view instanceof IndicatorCard)
        {
            IndicatorCard card = (IndicatorCard)view;

            onCardSelected(card);
        }
    }

    /**
     * Sets the desired selected indicator option
     *
     * @param indicatorCard
     */
    private void onCardSelected(@Nullable IndicatorCard indicatorCard) {

        if(indicatorCard.equals(selectedIndicatorCard))
        {
            indicatorCard.setSelected(false);
            mSurveyViewModel.removeIndicatorResponse(question);
            selectedIndicatorCard = null;
        }
        else
        {
            mRedCard.setSelected(mRedCard.equals(indicatorCard));
            mYellowCard.setSelected(mYellowCard.equals(indicatorCard));
            mGreenCard.setSelected(mGreenCard.equals(indicatorCard));

            mSurveyViewModel.addIndicatorResponse(question, indicatorCard.getOption());
            selectedIndicatorCard = indicatorCard;
        }

    }
}
