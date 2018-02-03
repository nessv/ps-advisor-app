package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Fragment for settings tab
 */

public class SettingsTabFrag extends TabbedFrag
{
    @Override
    protected StackedFrag getInitialFragment() {
        return UnderConstructionFragment.build(getResources().getString(R.string.settingstab_title));
    }
}
