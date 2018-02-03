package org.fundacionparaguaya.advisorapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Just an example of the TabbedFrag class. Jee wiz, isn't this easy?
 *
 */

public class ExampleTabbedFragment extends TabbedFrag
{
    @Override
    protected StackedFrag getInitialFragment() {
        return ExampleStackedFragment.build(1);
    }
}
