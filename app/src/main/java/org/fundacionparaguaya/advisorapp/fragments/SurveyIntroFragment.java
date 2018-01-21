package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

        //get text view

        //set family name
    }

    void onSubmit()
    {
        mSurveyCallback.onFinish(null);
    }
}
