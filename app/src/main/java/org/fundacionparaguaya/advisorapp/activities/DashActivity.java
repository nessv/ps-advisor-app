package org.fundacionparaguaya.advisorapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.*;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.DisplayBackNavListener;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;

import java.lang.reflect.InvocationTargetException;

public class DashActivity extends AbstractFragSwitcherActivity implements DisplayBackNavListener
{
    DashboardTabBarView tabBarView;

	@Override
    public void onBackPressed()
    {
        ((TabbedFrag)getFragment(getClassForType(tabBarView.getSelected()))).onNavigateBack();
    }

    private Class getClassForType(DashboardTab.TabType type) {
        switch (type)
        {
            case FAMILY:
                return FamilyTabbedFragment.class;
            case MAP:
                return ExampleTabbedFragment.class;
            case ARCHIVE:
                return ExampleTabbedFragment.class;
            case SETTINGS:
                return ExampleTabbedFragment.class;
        }

        return null;
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        if(!hasFragForClass(getClassForType(event.getSelectedTab()))) {
            constructFragment(getClassForType(event.getSelectedTab()));
        }

        switchToFrag(getClassForType(event.getSelectedTab()));

        Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
    };

	private void constructFragment(Class fragClass)
    {
        try{
            addFragment((Fragment) fragClass.getConstructor().newInstance());
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);
        tabBarView.addTabSelectedHandler(handler);

        setFragmentContainer(R.id.dash_content);

        if(!hasFragForClass(FamilyTabbedFragment.class)) {
            constructFragment(FamilyTabbedFragment.class);
        }

        switchToFrag(FamilyTabbedFragment.class);
    }



    @Override
    public void onShowBackNav()
    {
        Toast.makeText(getApplicationContext(), "Show Back Nav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideBackNav()
    {
        Toast.makeText(getApplicationContext(), "Hide Back Nav", Toast.LENGTH_SHORT).show();
    }
}
