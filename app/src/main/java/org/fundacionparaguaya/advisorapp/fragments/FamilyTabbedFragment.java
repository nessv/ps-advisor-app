package org.fundacionparaguaya.advisorapp.fragments;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Tab for a family
 *
 */

public class FamilyTabbedFragment extends AbstractTabbedFrag
{

    String title = "Family";
    public FamilyTabbedFragment()
    {
        super();
        setTabTitle(title);
    }

    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return new AllFamiliesStackedFrag();
    }
}
