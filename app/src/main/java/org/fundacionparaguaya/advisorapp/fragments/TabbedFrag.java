package org.fundacionparaguaya.advisorapp.fragments;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by benhylak on 1/14/18.
 */

public abstract class TabbedFrag
{
    Stack<StackedFrag> mFragStack;
    ArrayList<BackNavRequiredChangeHandler> mRequiresBackNavHandlers;

    protected void navigateNext(StackedFrag frag)
    {
        //add current to stack
        //launch next
        //check if stack is larger than one
        //if so, notify BackNav
    }

    public void onNavigateBack()
    {
        //check if stack is equal to 1
        //if so, notifyBackNavNotRequired()
    }

    protected void notifyBackNavNotRequired()
    {
        for(BackNavRequiredChangeHandler handler: mRequiresBackNavHandlers)
        {
            handler.handleBackNavChange(new BackNavRequiredChangeEvent(false));
        }
    }

    protected void notifyBackNavRequired()
    {
        for(BackNavRequiredChangeHandler handler: mRequiresBackNavHandlers)
        {
            handler.handleBackNavChange(new BackNavRequiredChangeEvent(true));
        }
    }

    class BackNavRequiredChangeEvent
    {
        boolean mRequired;

        BackNavRequiredChangeEvent(boolean required)
        {
            mRequired = required;
        }

        boolean isRequired()
        {
            return mRequired;
        }
    }

    interface BackNavRequiredChangeHandler
    {
        void handleBackNavChange(BackNavRequiredChangeEvent e);
    }
}
