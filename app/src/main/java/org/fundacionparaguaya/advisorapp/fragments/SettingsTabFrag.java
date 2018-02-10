package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Fragment for settings tab
 */

public class SettingsTabFrag extends AbstractTabbedFrag
{
    private String title = "Settings";
    public SettingsTabFrag(){
        super();
        setTabTitle(title);
    }

    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return UnderConstructionFragment.build(getResources().getString(R.string.settingstab_title));
    }
}
