package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by benhylak on 1/16/18.
 */

public class TestTabbedFragment extends TabbedFrag
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TestStackedFragment frag1 = new TestStackedFragment();
        Bundle bundleFrag1 = new Bundle();
        bundleFrag1.putString(TestStackedFragment.BUNDLE_ID_TEXT_TO_DISPLAY, "Fragment 1");
        frag1.setArguments(bundleFrag1);

        this.setInitialFragment(frag1);
    }
}
