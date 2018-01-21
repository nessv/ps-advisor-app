package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fundacionparaguaya.advisorapp.R;

/**
 *
 */

public class IndicatorFragment extends android.app.Fragment {

    public IndicatorFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        return rootView;
    }



}
