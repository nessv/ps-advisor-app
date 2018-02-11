package org.fundacionparaguaya.advisorapp.fragments;


/**
 * Fragment for settings tab
 */

public class SettingsTabFrag extends AbstractTabbedFrag
{
    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return new SettingsStackedFrag();
    }
}
