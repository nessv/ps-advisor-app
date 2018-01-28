package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Just an example of the TabbedFrag class. Jee wiz, isn't this easy?
 *
 */

public class FamilyTabbedFragment extends TabbedFrag
{
    AllFamiliesStackedFrag mFrag1;

    static String HAS_BEEN_INIT_KEY = "HAS_BEEN_INITIALIZED";
    //static String FAMILY_DETAIL_TAG = "FAMILY_DETAIL";

    boolean mHasBeenInitialized = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       // FragmentManager manager = getFragmentManager();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getChildFragmentManager().getFragments().size()==0)
        {
            mFrag1 = new AllFamiliesStackedFrag();
            this.setInitialFragment(mFrag1);
            mHasBeenInitialized = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(HAS_BEEN_INIT_KEY, mHasBeenInitialized);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
