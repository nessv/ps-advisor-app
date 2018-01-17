package org.fundacionparaguaya.advisorapp.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.fundacionparaguaya.advisorapp.R;

import java.util.ArrayList;

/**
 * This is a fragment that lives inside of a tab
 */

public abstract class TabbedFrag extends Fragment
{
    ArrayList<BackNavRequiredChangeHandler> mRequiresBackNavHandlers;

    private static final String TAG = "TabbedFrag";

    View mContentView;

    protected int mContainerId;

    boolean mWasBackNavRequired;

    public TabbedFrag()
    {
        mRequiresBackNavHandlers = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getChildFragmentManager().addOnBackStackChangedListener(() ->
        {
            //if there was a change in our state
            if(mWasBackNavRequired!=isBackNavRequired())
            {
                mWasBackNavRequired = isBackNavRequired();

                notifyBackNavRequiredChange(isBackNavRequired());
            }
        });

        mContainerId = View.generateViewId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = new LinearLayout(getActivity());
        mContentView.setId(mContainerId);

        return mContentView;
    }

    /**
     * Sets the initial stacked frag for this tabbed frag
     * @param frag Fragment to set
     */
    public void setInitialFragment(StackedFrag frag)
    {
       makeFragmentTransaction(frag).commit();
    }

    /**
     * Navigate to a new fragment
     *
     * @param frag Fragment to navigate to
     */
    public void navigateNext(StackedFrag frag)
    {
        makeFragmentTransaction(frag).addToBackStack(null).commit();
    }

    /**
     * Muscle behind setInitialFragment and navigateNext
     * @param frag
     */
    private FragmentTransaction makeFragmentTransaction(StackedFrag frag)
    {
        //frag.addNavEventHandler(this);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(mContainerId, frag);

        return ft;
    }

    /**
     * Navigate backwards in the stack
     */
    public void onNavigateBack()
    {
        getChildFragmentManager().popBackStack();
    }

    public void addBackNavRequiredHandler(BackNavRequiredChangeHandler handler)
    {
        this.mRequiresBackNavHandlers.add(handler);
    }

    public void removeBackNavRequiredHandler(BackNavRequiredChangeHandler handler)
    {
        this.mRequiresBackNavHandlers.remove(handler);
    }

    /**
     * Notifies listeners to hide or show the back navigation button
     *
     * @param required Whether or not to show back nav
     */
    protected void notifyBackNavRequiredChange(boolean required)
    {
        for(BackNavRequiredChangeHandler handler: mRequiresBackNavHandlers)
        {
            handler.handleBackNavChange(new BackNavRequiredChangeEvent(required));
        }
    }

    /**
     *
     * @return whether this fragment needs a button for back navigation
     */
    public boolean isBackNavRequired()
    {
        Log.d(TAG, "Back Stack Entry Count: " + getChildFragmentManager().getBackStackEntryCount());
        return getChildFragmentManager().getBackStackEntryCount() > 0;
    }

    public class BackNavRequiredChangeEvent
    {
        boolean mRequired;

        BackNavRequiredChangeEvent(boolean required)
        {
            mRequired = required;
        }

        public boolean isRequired()
        {
            return mRequired;
        }
    }

    public interface BackNavRequiredChangeHandler
    {
        void handleBackNavChange(BackNavRequiredChangeEvent e);
    }
}
