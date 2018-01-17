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
public class MainActivity extends AppCompatActivity implements TabbedFrag.BackNavRequiredChangeHandler
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
        switch (tabBarView.getSelected())
        {
            case FAMILY:
            {
                mFamiliesFrag.onNavigateBack();
                break;
            }

            case MAP:
            {
                mMapFrag.onNavigateBack();
                break;
            }

            case ARCHIVE:
            {
                mArchiveFrag.onNavigateBack();
                break;
            }

            case SETTINGS:
            {
                mSettingsFrag.onNavigateBack();
                break;
            }
        }
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        switch (event.getSelectedTab())
        {
            case FAMILY:
            {
                switchToFrag(mFamiliesFrag, event.getSelectedTab());
                break;
            }

            case MAP:
            {
                switchToFrag(mMapFrag, event.getSelectedTab());
                break;
            }

            case ARCHIVE:
            {
                switchToFrag(mArchiveFrag, event.getSelectedTab());
                break;
            }

            case SETTINGS:
            {
                switchToFrag(mSettingsFrag, event.getSelectedTab());
                break;
            }
        }

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
        tabBarView.addTabSelectedHandle(handler);

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

        getSupportFragmentManager().beginTransaction().attach(mFamiliesFrag).commit();
        getSupportFragmentManager().beginTransaction().attach(mMapFrag).commit();

        getSupportFragmentManager().beginTransaction().detach(mMapFrag).commit();
        getSupportFragmentManager().beginTransaction().detach(mFamiliesFrag).commit();

        switchToFrag(mFamiliesFrag, DashboardTab.TabType.FAMILY);

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.add(R.id.dash_content, mFamiliesFrag).commit();

        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void handleBackNavChange(TabbedFrag.BackNavRequiredChangeEvent e) {
        //switch (tabBarVie)
    }
}
