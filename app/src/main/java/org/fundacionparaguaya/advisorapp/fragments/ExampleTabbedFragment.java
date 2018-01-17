package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Just an example of the TabbedFrag class. Jee wiz, isn't this easy?
 *
 */

public class ExampleTabbedFragment extends TabbedFrag
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ExampleStackedFragment frag1 = ExampleStackedFragment.build(1);

        this.setInitialFragment(frag1);
    }
}
