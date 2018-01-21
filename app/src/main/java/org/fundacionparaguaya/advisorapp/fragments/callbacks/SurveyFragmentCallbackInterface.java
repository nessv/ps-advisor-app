package org.fundacionparaguaya.advisorapp.fragments.callbacks;

/**
 * Created by benhylak on 1/21/18.
 */

public interface SurveyFragmentCallbackInterface
{
    void onUpdateProgress(String questionsLeft, int progress);

    void onNavigate(); //should take a fragment or something
}
