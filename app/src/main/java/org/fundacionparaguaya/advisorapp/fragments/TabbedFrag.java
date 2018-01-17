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

public abstract class TabbedFrag extends Fragment implements StackedFrag.NavigationEventHandler
{
    ArrayList<BackNavRequiredChangeHandler> mRequiresBackNavHandlers;

    private static final String TAG = "TabbedFrag";

    View mContentView;

    boolean mWasBackNavRequired;

    public TabbedFrag()
    {
        mRequiresBackNavHandlers = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(getFragmentManager()!=null)
        {
            getFragmentManager().addOnBackStackChangedListener(() ->
            {
                //if there was a change in our state
                if(mWasBackNavRequired!=isBackNavRequired())
                {
                    mWasBackNavRequired = isBackNavRequired();
                    if (isBackNavRequired())
                    {
                        notifyBackNavRequired();
                    }
                    else
                    {
                        notifyBackNavNotRequired();
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = new LinearLayout(getActivity());
        mContentView.setId(R.id.stackedfrag_container);

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
    protected void navigateNext(StackedFrag frag)
    {
        makeFragmentTransaction(frag).addToBackStack("test").commit();
    }

    public void onNavigation(StackedFrag.NavigationEvent e)
    {
        navigateNext(e.mNextFrag);
    }

    /**
     * Muscle behind setInitialFragment and navigateNext
     * @param frag
     */
    private FragmentTransaction makeFragmentTransaction(StackedFrag frag)
    {
        frag.addNavEventHandler(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.stackedfrag_container, frag);

        return ft;
    }

    /**
     * Navigate backwards in the stack
     */
    public void onNavigateBack()
    {
        if(getFragmentManager()!=null)
        {
            getFragmentManager().popBackStack();
        }
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
     * Notifies listeners to hide the back navigation button
     */
    protected void notifyBackNavNotRequired()
    {
        for(BackNavRequiredChangeHandler handler: mRequiresBackNavHandlers)
        {
            handler.handleBackNavChange(new BackNavRequiredChangeEvent(false));
        }
    }

    /**
     * Notifies listeners that we need back navigation
     */
    protected void notifyBackNavRequired()
    {
        for(BackNavRequiredChangeHandler handler: mRequiresBackNavHandlers)
        {
            handler.handleBackNavChange(new BackNavRequiredChangeEvent(true));
        }
    }

    /**
     *
     * @return whether this fragment needs a button for back navigation
     */
    public boolean isBackNavRequired()
    {
        getFragmentManager().getBackStackEntryCount();

        Log.d(TAG, "Back Stack Entry Count: " + getFragmentManager().getBackStackEntryCount());
        return getFragmentManager().getBackStackEntryCount() > 0;
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
