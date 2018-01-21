package org.fundacionparaguaya.advisorapp.activities;

import android.app.Fragment;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.fragments.StackedFrag;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.SurveyFragmentCallbackInterface;
import org.fundacionparaguaya.advisorapp.models.Snapshot;

/**
 * Activity for surveying a family's situation. Displays the fragments that record background info and allows
 * the family to select indicators
 */

public class SurveyActivity extends AbstractFragSwitcherActivity implements SurveyFragmentCallbackInterface
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onFinish(Snapshot snap)
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

    }

    @Override
    public void hideFooter()
    {

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
