package org.fundacionparaguaya.advisorapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTab;
import org.fundacionparaguaya.advisorapp.viewcomponents.DashboardTabBarView;

public class MainActivity extends AppCompatActivity
{
    private DashboardTabBarView tabBarView;

    private DashboardTabBarView.TabSelectedHandler handler = new DashboardTabBarView.TabSelectedHandler() {
        @Override
        public void onTabSelection(DashboardTabBarView.TabSelectedEvent event) {
            Toast.makeText(getApplicationContext(), event.getSelectedTab().name(), Toast.LENGTH_SHORT).show();
        }
    };
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabBarView = (DashboardTabBarView) findViewById(R.id.dashboardTabView);
        tabBarView.addTabSelectedHandle(handler);
    }
}
