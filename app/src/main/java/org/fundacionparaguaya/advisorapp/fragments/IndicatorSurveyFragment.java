package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.IndicatorAdapter;

/**
 * Created by alex on 1/23/2018.
 */

public class IndicatorSurveyFragment extends AbstractSurveyFragment {

    IndicatorAdapter mAdapter;

    ViewPager mPager;

    @Override
    public void onCreate(@Nullable Bundle savedInsanceState) {
        super.onCreate(savedInsanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_surveyindicatorsurvey, container, false);

        mAdapter = new IndicatorAdapter(getFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.indicatorsurvey_viewpager);

        mPager.setAdapter(mAdapter);

        return view;
    }

    void onSubmit(){

    }

    public static IndicatorSurveyFragment build(){
        IndicatorSurveyFragment fragment = new IndicatorSurveyFragment();
        return fragment;
    }

}
