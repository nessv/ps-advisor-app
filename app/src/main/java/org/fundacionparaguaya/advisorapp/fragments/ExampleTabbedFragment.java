package org.fundacionparaguaya.advisorapp.fragments;

/**
 * Just an example of the AbstractTabbedFrag class. Jee wiz, isn't this easy?
 *
 */

public class ExampleTabbedFragment extends AbstractTabbedFrag
{
    @Override
    protected AbstractStackedFrag makeInitialFragment() {
        return ExampleStackedFragment.build(1);
    }
}
