package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.IndicatorCard;

import java.util.ArrayList;

/**
 *
 */

public class ChooseIndicatorFragment extends AbstractSurveyFragment {

    IndicatorCard mGreenCard;
    IndicatorCard mYellowCard;
    IndicatorCard mRedCard;

    IndicatorQuestion question;

    SurveyIndicatorsFragment parentFragment;

    IndicatorAdapter adapter;

    @Nullable
    IndicatorCard selectedIndicatorCard;
    private CountDownTimer nextPageTimer;

    public ChooseIndicatorFragment newInstance(IndicatorAdapter adapter, IndicatorQuestion question) {

        ChooseIndicatorFragment fragment = new ChooseIndicatorFragment();
        this.adapter = adapter;
        this.question = question;

        parentFragment = (SurveyIndicatorsFragment) adapter.returnParent();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chooseindicator, container, false);

        mGreenCard = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_green);
        mYellowCard = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_yellow);
        mRedCard = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_red);

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

        IndicatorOption existingResponse = parentFragment.getResponses(question);

        if (existingResponse != null) {
            switch (existingResponse.getLevel()) {
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

        mGreenCard.addIndicatorSelectedHandler(handler);
        mYellowCard.addIndicatorSelectedHandler(handler);
        mRedCard.addIndicatorSelectedHandler(handler);
        return rootView;
    }

    private IndicatorCard.IndicatorSelectedHandler handler = (card) ->
    {
        if (parentFragment.isPageChanged()) {
            onCardSelected(card);
        }
    };

    /**
     * Sets the desired selected indicator option
     *
     * @param indicatorCard
     */
    private void onCardSelected(@Nullable IndicatorCard indicatorCard) {

        if (indicatorCard.equals(selectedIndicatorCard)) {
            indicatorCard.setSelected(false);
            parentFragment.removeIndicatorResponse(question);
            selectedIndicatorCard = null;
        } else {
            mRedCard.setSelected(mRedCard.equals(indicatorCard));
            mYellowCard.setSelected(mYellowCard.equals(indicatorCard));
            mGreenCard.setSelected(mGreenCard.equals(indicatorCard));

            parentFragment.addIndicatorResponse(question, indicatorCard.getOption());
            updateParent();

            selectedIndicatorCard = indicatorCard;
        }

    }

    public boolean isCardSelected() {
        if (selectedIndicatorCard == null) {
            return false;
        }
        return true;
    }

    private void updateParent() {
        if (nextPageTimer !=null){
            nextPageTimer.cancel();
            nextPageTimer = null;
        } else {
            nextPageTimer = new CountDownTimer(500, 100) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (selectedIndicatorCard != null) {
                        parentFragment.nextQuestion();
                    } else {
                        parentFragment.removeIndicatorResponse(question);
                    }
                }
            }.start();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
