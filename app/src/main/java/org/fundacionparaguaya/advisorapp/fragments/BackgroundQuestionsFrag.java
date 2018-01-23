package org.fundacionparaguaya.advisorapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.viewmodels.AllFamiliesViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import javax.inject.Inject;

/**
 * Created by Mone Elokda on 1/22/2018.
 */

public class BackgroundQuestionsFrag extends Fragment {

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    //AllFamiliesViewModel mAllFamiliesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);


    }




}
