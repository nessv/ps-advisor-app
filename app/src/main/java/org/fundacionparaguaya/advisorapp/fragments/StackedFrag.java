package org.fundacionparaguaya.advisorapp.fragments;

import java.util.List;

/**
 * Created by benhylak on 1/14/18.
 */

public abstract class StackedFrag
{
    class NavigationEvent
    {
        StackedFrag mNextFrag;

        NavigationEvent(StackedFrag nextFrag)
        {
            mNextFrag = nextFrag;
        }
    }

    interface NavigationEventHandler
    {
        public void onNavigation(NavigationEvent e);
    }

    List<NavigationEventHandler> mNavigationEventHandlers;

    /**Constructs fragment, sets args**/
    /**Should provide alternate definitions for each fragment**/
    public abstract void build();

    /**
     *
     * @param e NavigationEvent
     */
    protected void notifyNavigtion(NavigationEvent e)
    {
        for(NavigationEventHandler h: mNavigationEventHandlers)
        {
            h.onNavigation(e);
        }
    }

}
