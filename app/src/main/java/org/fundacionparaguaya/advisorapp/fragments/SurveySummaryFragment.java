package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by alex on 1/25/2018.
 */

public class SurveySummaryFragment extends AbstractSurveyFragment {


    SharedSurveyViewModel mSurveyViewModel;

    LinearLayout linearLayout;

    public SurveySummaryFragment newInstance(SharedSurveyViewModel surveyViewModel){

        SurveySummaryFragment fragment = new SurveySummaryFragment();
        mSurveyViewModel = surveyViewModel;

        return fragment;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_surveysummary, container, false);

        linearLayout = view.findViewById(R.id.surveysummary_fragment);

        ArrayList<String> skippedQs = new ArrayList<>();

        for ( IndicatorQuestion skippedQuestions : mSurveyViewModel.getSkippedIndicators()){
                skippedQs.add(skippedQuestions.getName());
        }

        for( int i = 0; i < skippedQs.size(); i++ )
        {
            TextView textView = new TextView(getContext());
            textView.setText(skippedQs.get(i));
            linearLayout.addView(textView);
        }

        return view;
    }



}
