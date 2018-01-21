package org.fundacionparaguaya.advisorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.ExampleTabbedFragment;
import org.fundacionparaguaya.advisorapp.fragments.FamilyTabbedFragment;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;

public class DashActivity extends AppCompatActivity implements TabbedFrag.BackNavRequiredChangeHandler
{
    DashboardTabBarView tabBarView;
    TabbedFrag mFamiliesFrag;
    TabbedFrag mMapFrag;
    TabbedFrag mArchiveFrag;
    TabbedFrag mSettingsFrag;

    TabbedFrag mLastFrag;

	@Override
    public void onBackPressed()
    {
        getFragForType(tabBarView.getSelected()).onNavigateBack();
    }

    private TabbedFrag getFragForType(DashboardTab.TabType type) {
        switch (type)
        {
            case FAMILY:
                return mFamiliesFrag;
            case MAP:
                return mMapFrag;
            case ARCHIVE:
                return mArchiveFrag;
            case SETTINGS:
                return mSettingsFrag;
        }

        return null;
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        switchToFrag(getFragForType(event.getSelectedTab()), event.getSelectedTab());

        Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
    };

    protected void switchToFrag(TabbedFrag frag, DashboardTab.TabType type)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(mLastFrag!=null) {
            ft.detach(mLastFrag);
        }

        ft.attach(frag).replace(R.id.dash_content, frag).commit();

        mLastFrag = frag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);
        tabBarView.addTabSelectedHandler(handler);

        /**
         * Create fragment for each tab
         */
        mFamiliesFrag = new FamilyTabbedFragment();
        mMapFrag = new ExampleTabbedFragment();
        mArchiveFrag = new ExampleTabbedFragment();
        mSettingsFrag = new ExampleTabbedFragment();

        mFamiliesFrag.addBackNavRequiredHandler((event) -> {
            String text;

            if(event.isRequired()) text ="Is Required";
            else text = "Not required";

            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        });

        initTabs(mFamiliesFrag, mMapFrag);

        switchToFrag(mFamiliesFrag, DashboardTab.TabType.FAMILY);

        //TODO: right now, hitting "back" will circumvent the login screen.
        // also, this should be called before everything in this function, and check if already authenticated.
        //maybe we could have a separate main activity that accomplishes this all

        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    /**
     * This looks confusing, but it is necessary to attach and detach all of the tabs so their placed into
     * Fragment manager. Otherwise, the tab system won't work correctly.
     *
     *
     * @param tabs Tabs to add to support fragment manager
     */
    public void initTabs(TabbedFrag ... tabs)
    {
        for(TabbedFrag tab: tabs)
        {
            getSupportFragmentManager().beginTransaction().attach(tab).commit();
            getSupportFragmentManager().beginTransaction().detach(tab).commit();
        }
    }

    @Override
    public void handleBackNavChange(TabbedFrag.BackNavRequiredChangeEvent e) {
        //switch (tabBarVie)
    }
}
