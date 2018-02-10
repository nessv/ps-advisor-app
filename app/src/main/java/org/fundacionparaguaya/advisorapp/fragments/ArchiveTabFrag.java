package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Tab that shows archived families, and eventually families that have been shared with them
 */

public class ArchiveTabFrag extends AbstractTabbedFrag
{

    private String title = "Archive";
    public ArchiveTabFrag(){
        super();
        setTabTitle(title);
    }

    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return UnderConstructionFragment.build(getResources().getString(R.string.archivetab_title));
    }
}
