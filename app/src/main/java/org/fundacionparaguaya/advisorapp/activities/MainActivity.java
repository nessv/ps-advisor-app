package org.fundacionparaguaya.advisorapp.activities;

import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.fragments.ExampleTabbedFragment;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;
public class MainActivity extends AppCompatActivity
{

private DashboardTabBarView tabBarView;
    TabbedFrag mTabbedFrag;

	@Override
    public void onBackPressed()
    {
        mTabbedFrag.onNavigateBack();
    }

    private DashboardTabBarView.TabSelectedHandler handler = (event) ->
    {
        switch (event.getSelectedTab())
        {
            case FAMILY:
            {

            }
        }

        Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
    };

@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);
        tabBarView.addTabSelectedHandle(handler);

        mTabbedFrag = new ExampleTabbedFragment();

        mTabbedFrag.addBackNavRequiredHandler((event) -> {
            String text;

            if(event.isRequired()) text ="Is Required";
            else text = "Not required";

            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.dash_content, mTabbedFrag).commit();
    }
}
