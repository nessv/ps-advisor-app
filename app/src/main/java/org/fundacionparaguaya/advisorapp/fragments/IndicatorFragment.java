package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.IndicatorCard;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 */

public class IndicatorFragment extends AbstractSurveyFragment {

    IndicatorCard mGreenIndicator;
    IndicatorCard mYellowIndicator;
    IndicatorCard mRedIndicator;

    @Nullable
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSurveyViewModel;

    public static IndicatorFragment newInstance(
            String greenImage,    String greenText,
            String yellowImage,   String yellowText,
            String redImage,      String redText
    ){
        IndicatorFragment fragment = new IndicatorFragment();
        //Set Green Indicator
        fragment.mGreenIndicator.setImage(Uri.parse(greenImage));
        fragment.mGreenIndicator.setText(greenText);

        //Set Yellow Indicator
        fragment.mYellowIndicator.setImage(Uri.parse(yellowImage));
        fragment.mYellowIndicator.setText(yellowText);

        //Set Red Indicator
        fragment.mRedIndicator.setImage(Uri.parse(redImage));
        fragment.mRedIndicator.setText(redText);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_surveyindicators, container, false);

        mGreenIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_green);
        mYellowIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_yellow);
        mRedIndicator = (IndicatorCard) rootView.findViewById(R.id.indicatorcard_red);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSurveyViewModel = ViewModelProviders
                .of(getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

    }



}
