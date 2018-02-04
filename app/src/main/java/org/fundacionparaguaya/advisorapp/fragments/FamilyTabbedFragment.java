package org.fundacionparaguaya.advisorapp.fragments;

/**
 * Tab for a family
 *
 */

public class FamilyTabbedFragment extends AbstractTabbedFrag
{
    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return new AllFamiliesStackedFrag();
    }
}
