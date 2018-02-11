package org.fundacionparaguaya.advisorapp.fragments;


/**
 * Tab for a family
 *
 */

public class FamilyTabbedFragment extends AbstractTabbedFrag
{

    public FamilyTabbedFragment()
    {
        super();
        setTabTitle("Family");
    }

    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return new AllFamiliesStackedFrag();
    }
}
