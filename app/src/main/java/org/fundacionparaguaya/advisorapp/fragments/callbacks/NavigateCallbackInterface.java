package org.fundacionparaguaya.advisorapp.fragments.callbacks;

import org.fundacionparaguaya.advisorapp.fragments.StackedFrag;

/**
 * Call back for fragments that want their parent fragment/activity to navigate
 */

public interface NavigateCallbackInterface
{
    void navigateNext(StackedFrag frag);
}
