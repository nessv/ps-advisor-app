package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Tab for a family
 *
 */

public class FamilyTabbedFragment extends TabbedFrag
{
    @Override
    protected StackedFrag getInitialFragment() {
        return new AllFamiliesStackedFrag();
    }
}
