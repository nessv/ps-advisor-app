package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Tab that will show all of the families the asesora is working with on a map
 */

public class MapTabFrag extends AbstractTabbedFrag {
    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return UnderConstructionFragment.build(getResources().getString(R.string.maptab_title));
    }
}
