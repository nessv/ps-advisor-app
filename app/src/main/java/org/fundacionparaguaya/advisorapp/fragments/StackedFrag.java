package org.fundacionparaguaya.advisorapp.fragments;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a fragment that is managed by a tabbed fragment... and can be stacked
 */

public abstract class StackedFrag extends Fragment
{
    List<NavigationEventHandler> mNavigationEventHandlers;

    private static final String TAG = "StackedFrag";

    public StackedFrag()
    {
        mNavigationEventHandlers = new ArrayList<>();
    }

    static class NavigationEvent
    {
        StackedFrag mNextFrag;

        NavigationEvent(StackedFrag nextFrag)
        {
            mNextFrag = nextFrag;
        }
    }

    /**
     * Add a handler for this fragment's navigation events
     */
    public void addNavEventHandler(NavigationEventHandler h)
    {
        this.mNavigationEventHandlers.add(h);
    }

    interface NavigationEventHandler
    {
        void onNavigation(NavigationEvent e);
    }


    /**
     *
     * @param e NavigationEvent
     */
    protected void notifyNavigtionHandlers(NavigationEvent e)
    {
        for(NavigationEventHandler h: mNavigationEventHandlers)
        {
            h.onNavigation(e);
        }
    }

}
