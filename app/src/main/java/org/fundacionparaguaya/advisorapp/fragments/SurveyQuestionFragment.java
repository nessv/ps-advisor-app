package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.R;

/**
 *
 */

public class SurveyQuestionFragment extends android.app.Fragment {

    public SurveyQuestionFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        return rootView;
    }



}
