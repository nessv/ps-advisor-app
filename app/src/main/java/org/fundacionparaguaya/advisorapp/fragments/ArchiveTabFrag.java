package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Tab that shows archived families, and eventually families that have been shared with them
 */

public class ArchiveTabFrag extends TabbedFrag
{
    @Override
    protected StackedFrag getInitialFragment() {
        return UnderConstructionFragment.build(getResources().getString(R.string.archivetab_title));
    }
}
