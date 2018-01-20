package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.viewmodels.EXAMPLEViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import javax.inject.Inject;

public class EXAMPLEFragment extends Fragment {

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    EXAMPLEViewModel mEXAMPLEViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mEXAMPLEViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(EXAMPLEViewModel.class);
    }
}
