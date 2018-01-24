package org.fundacionparaguaya.advisorapp.activities;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.BackgroundQuestionsFrag;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Extending this class makes it easier to switch between fragments within the activity's container,
 * without losing the fragment's state
 *
 * @author benhylak
 */

public abstract class AbstractFragSwitcherActivity extends AppCompatActivity
{
    Fragment mLastFrag;

    private int mFragmentContainer;

    private Map<Class, String> fragmentTags = new HashMap<>();

    /**
     * Sets the container for the fragments and attaches/detatches so their state is tracked by fragment manager
     *
     *
     * @param resourceId Container that holds fragment
     */
    public void setFragmentContainer(int resourceId)
    {
        mFragmentContainer = resourceId;

    }

    public void addFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().add(fragment, fragment.getClass().getName()).commit();
        getSupportFragmentManager().executePendingTransactions();

        Fragment f = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName());
        getSupportFragmentManager().beginTransaction().detach(f).commit();
    }

    protected Fragment getFragment(Class fragmentClass)
    {
        return getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
    }

    /**
     * Detatches the currently attached fragment and replaces it with the specific fragment
     *
     * @param fragmentClass Class of the fragment to switch to
     */
    protected void switchToFrag(Class fragmentClass)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(mLastFrag!=null) {
            getSupportFragmentManager().beginTransaction().detach(mLastFrag).commit();
        }

        getSupportFragmentManager().executePendingTransactions();

        Fragment f = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
        ft.attach(f).replace(mFragmentContainer, f).commit();

        mLastFrag = f;
    }

    protected boolean hasFragForClass(Class fragmentClass)
    {
        return (getSupportFragmentManager().findFragmentByTag(fragmentClass.getName())) != null;
    }
}
