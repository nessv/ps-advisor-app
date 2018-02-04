package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import org.fundacionparaguaya.advisorapp.fragments.AbstractStackedFrag;

/**
 * Call back for fragments that want their parent fragment/activity to navigate
 */

public interface NavigationListener
{
    void onNavigateNext(AbstractStackedFrag frag);

    void onNavigateBack();
}
