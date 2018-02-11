package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SettingsViewModel;

import javax.inject.Inject;

/**
 * Created by alex on 2/10/2018.
 */

public class SettingsStackedFrag extends AbstractStackedFrag {

    private Button mLogout;
    private AuthenticationManager mAuthManager;

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    SettingsViewModel mSettingsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent().inject(this);

        mSettingsViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(SettingsViewModel.class);

        mAuthManager = mSettingsViewModel.getAuthManager();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settingsmain, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mLogout = (Button) view.findViewById(R.id.button_settingsmain_logout);

        mLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuthManager.logout();
            }
        });

    }

}
