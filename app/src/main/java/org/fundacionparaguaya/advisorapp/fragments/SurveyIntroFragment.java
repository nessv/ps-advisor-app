package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.SurveyFragmentCallbackInterface;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

/**
 * Intro page on a new survey
 */

public class SurveyIntroFragment extends Fragment
{
    public static String FAMILY_NAME_KEY = "FAMILY_NAME";

    SurveyFragmentCallbackInterface mSurveyCallback;
    //need the family name

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //get family name from arguments

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_surveyintro, container, false);

        //get text view

        //set family name

        return view;
    }

    void onSubmit()
    {
        mSurveyCallback.onFinish(null);
    }

    public static SurveyIntroFragment build(String familyName)
    {
        Bundle args = new Bundle();
        args.putString(FAMILY_NAME_KEY, familyName);

        SurveyIntroFragment fragment = new SurveyIntroFragment();

        fragment.setArguments(args);

        return fragment;
    }
}
