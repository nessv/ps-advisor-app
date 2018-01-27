package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yarolegovich.discretescrollview.transform.Pivot;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.IndicatorCard;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;

/**
 *
 */

public class ChooseIndicatorFragment extends AbstractSurveyFragment {

    IndicatorCard mGreenIndicator;
    IndicatorCard mYellowIndicator;
    IndicatorCard mRedIndicator;

    ArrayList<IndicatorCard> mCards = new ArrayList();

    IndicatorQuestion question;

    SurveyIndicatorsFragment parentFragment;

    IndicatorAdapter adapter;

    IndicatorOption mGreenOption;
    IndicatorOption mYellowOption;
    IndicatorOption mRedOption;

    @Nullable
    IndicatorCard selectedIndicator;

    public ChooseIndicatorFragment newInstance(IndicatorAdapter adapter, IndicatorQuestion question) {

        ChooseIndicatorFragment fragment = new ChooseIndicatorFragment();
        this.adapter = adapter;
        this.question = question;

        for (IndicatorOption option : question.getOptions()) {
            switch (option.getLevel()) {
                case Green:
                    mGreenOption = option;
                    break;
                case Yellow:
                    mYellowOption = option;
                    break;
                case Red:
                    mRedOption = option;
                    break;
            }
        }

        parentFragment = (SurveyIndicatorsFragment) adapter.returnParent();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chooseindicator, container, false);

        mGreenIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_green);
        mYellowIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_yellow);
        mRedIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_red);

        mCards.add(mGreenIndicator);
        mCards.add(mYellowIndicator);
        mCards.add(mRedIndicator);

        //Set Green Indicator
        mGreenIndicator.setImage(Uri.parse(mGreenOption.getImageUrl()));
        mGreenIndicator.setText(mGreenOption.getDescription());

        //Set Yellow Indicator
        mYellowIndicator.setImage(Uri.parse(mYellowOption.getImageUrl()));
        mYellowIndicator.setText(mYellowOption.getDescription());

        //Set Red Indicator
        mRedIndicator.setImage(Uri.parse(mRedOption.getImageUrl()));
        mRedIndicator.setText(mRedOption.getDescription());

        IndicatorOption responses = parentFragment.getResponses(question);

        try {
            if (responses.equals(question.getOptions().get(0))) {
                mGreenIndicator.setSelected(true);
            } else if (responses.equals(question.getOptions().get(1))) {
                mYellowIndicator.setSelected(true);
            } else if (responses.equals(question.getOptions().get(2))) {
                mRedIndicator.setSelected(true);
            }
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Null Pointer", Toast.LENGTH_SHORT).show();
        }

        mGreenIndicator.setOnClickListener((event) -> {
            onSelect(mGreenIndicator, mGreenOption);
        });

        mYellowIndicator.setOnClickListener((event) -> {
            onSelect(mYellowIndicator, mYellowOption);
        });

        mRedIndicator.setOnClickListener((event) -> {
            onSelect(mRedIndicator, mRedOption);
        });
        return rootView;


    }

    private void onSelect(IndicatorCard indicator, IndicatorOption option) {
        if (indicator.equals(selectedIndicator)) {
            indicator.setSelected(false);
            setSelected(null, null);
            selectedIndicator = null;
        } else {
            setSelected(indicator, option);
            selectedIndicator = indicator;
        }
    }

    /**
     * Sets the desired selected indicator
     *
     * @param indicator
     */
    private void setSelected(@Nullable IndicatorCard indicator, @Nullable IndicatorOption option) {
        if (indicator == null) {
            parentFragment.removeIndicatorResponse(question);
        } else {
            for (IndicatorCard indicatorCard : mCards) {
                if (indicatorCard.equals(indicator)) {
                    indicatorCard.setSelected(true);
                } else {
                    indicatorCard.setSelected(false);
                }
            }
            parentFragment.addIndicatorResponse(question, option);
            updateParent();
        }

    }

    public boolean isCardSelected() {
        if (selectedIndicator.equals(null)) {
            return false;
        }
        return true;
    }

    private void updateParent() {

        if (parentFragment != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    parentFragment.nextQuestion();
                }
            }, 500); // Millisecond 1000 = 1 sec
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
