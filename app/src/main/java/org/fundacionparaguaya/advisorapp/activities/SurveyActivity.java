package org.fundacionparaguaya.advisorapp.activities;

import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.StackedFrag;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.SurveyFragmentCallbackInterface;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

/**
 * Activity for surveying a family's situation. Displays the fragments that record background info and allows
 * the family to select indicators
 */

public class SurveyActivity extends AbstractFragSwitcherActivity implements SurveyFragmentCallbackInterface
{
    SurveyIntroFragment introFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        setFragmentContainer(R.id.survey_activity_fragment_container);

        introFragment = SurveyIntroFragment.build("Smith");

        initFrags(introFragment);

        showIntro();
    }

    @Override
    public void onFinish(Snapshot snap)
    {

    }

    void showIntro()
    {
        switchToFrag(introFragment);
        //build intro fragment
        //create transaction
    }

    void showBackgroundQuestions()
    {
        //load background questions fragment. give it the questions it needs display
    }

    void showIndicators()
    {
        //load indicators fragment. give it the snapshot, and survey, so it has indicators
        //and responses. It'll load one at a time, and if answered, make it selected.
        //should also take a start index
    }

    void showConfirmation()
    {

    }

    //welcome screen
    @Override
    public void onUpdateProgress(String questionsLeft, int progress)
    {
        //update progress bar
    }

    @Override
    public void setHeaderFooterColor()
    {

    }

    @Override
    public void setTitle()
    {

    }

    @Override
    public void hideHeader()
    {
        //set header to GONE
    }

    @Override
    public void hideFooter()
    {
        //set footer to GONE
    }

    @Override
    public void showHeader()
    {

    }

    @Override
    public void showFooter()
    {

    }

    @Nullable
    @Override
    public LiveData<Snapshot> getSnapshot()
    {
        return null;
    }

    @Override
    public void onNavigateNext(StackedFrag frag)
    {

    }

    //Progress Bar
    //ProgressUpdated event
    //Close button with dialog
    //SetTitle

}
