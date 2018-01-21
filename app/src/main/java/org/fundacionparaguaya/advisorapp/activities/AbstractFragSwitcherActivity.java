package org.fundacionparaguaya.advisorapp.activities;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;

/**
 * Created by benhylak on 1/21/18.f
 */

public abstract class AbstractFragSwitcherActivity extends AppCompatActivity
{
    Fragment mLastFrag;

    //must be set by extending activity
    private int mFragmentContainer;

    protected void setFragmentContainer(int resourceId)
    {
        mFragmentContainer = resourceId;
    }

    protected void switchToFrag(Fragment frag)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(mLastFrag!=null) {
            ft.detach(mLastFrag);
        }

        ft.attach(frag).replace(mFragmentContainer, frag).commit();

        mLastFrag = frag;
    }

    /** Replaces the current fragment without saving the old fragment's state*/
    protected void removeThenSwitchFrag(Fragment frag)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(mLastFrag).commit();

        mLastFrag=null;

        switchToFrag(frag);
    }

    /**
     * This looks confusing, but it is necessary to attach and detach all of the fragments so their placed into
     * Fragment manager. Otherwise, switching between can be messy.
     *
     * @param fragments Frags to add to fragment manager
     */
    public void initFrags(Fragment ... fragments)
    {
        for(Fragment frag: fragments)
        {
            getSupportFragmentManager().beginTransaction().attach(frag).commit();
            getSupportFragmentManager().beginTransaction().detach(frag).commit();
        }
    }
}
