package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.R;

import java.util.ArrayList;

/**
 * Main side tab for dashboard
 */


public class DashboardTabBarView extends LinearLayout {
    LinearLayout dashboardTabView;
    ImageView fpLogo;
    DashboardTab familyTab;
    DashboardTab mapTab;
    DashboardTab archiveTab;
    DashboardTab settingsTab;
    ImageButton bugButton;

    public enum Tab{
        family_tab,
        map_tab,
        archive_tab,
        setting_tab
    }

    private ArrayList <TabSelectedHandler> tabSelectedHandlers = new ArrayList();


    public DashboardTabBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtabview, this);

        dashboardTabView = (LinearLayout) findViewById(R.id.dashboardtabview);
        fpLogo = (ImageView) findViewById(R.id.fp_logo);
        familyTab = (DashboardTab) findViewById(R.id.family_tab);
        mapTab = (DashboardTab) findViewById(R.id.map_tab);
        archiveTab = (DashboardTab) findViewById(R.id.archive_tab);
        settingsTab = (DashboardTab) findViewById(R.id.settings_tab);
        bugButton = (ImageButton) findViewById(R.id.bug_button);

        familyTab.setOnClickListener(clickListener);
        mapTab.setOnClickListener(clickListener);
        archiveTab.setOnClickListener(clickListener);
        settingsTab.setOnClickListener(clickListener);

        bugButton.setOnClickListener(clickListener);
    }

    public interface TabSelectedHandler {
        void onTabSelection(TabSelectedEvent event);
    }

    public class TabSelectedEvent {
        private Tab selectedTab;
        TabSelectedEvent(Tab selectedTab){
            this.selectedTab = selectedTab;
        }
        public Tab getSelectedTab() {
            return selectedTab;
        }
    }

    public void addTabSelectedHandle(TabSelectedHandler handler){
        tabSelectedHandlers.add(handler);
    }

    private void notifyHandlers(Tab tab){
        for(int counter=0; counter < tabSelectedHandlers.size(); counter++){
            tabSelectedHandlers.get(counter).onTabSelection(new TabSelectedEvent(tab));
        }
    }

    final OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()){
                case R.id.family_tab:
                    familyTab.setSelected(true);
                    mapTab.setSelected(false);
                    archiveTab.setSelected(false);
                    settingsTab.setSelected(false);
                    notifyHandlers(Tab.family_tab);
                    break;
                case R.id.map_tab:
                    familyTab.setSelected(false);
                    mapTab.setSelected(true);
                    archiveTab.setSelected(false);
                    settingsTab.setSelected(false);
                    notifyHandlers(Tab.map_tab);
                    break;
                case R.id.archive_tab:
                    familyTab.setSelected(false);
                    mapTab.setSelected(false);
                    archiveTab.setSelected(true);
                    settingsTab.setSelected(false);
                    notifyHandlers(Tab.archive_tab);
                    break;
                case R.id.settings_tab:
                    familyTab.setSelected(false);
                    mapTab.setSelected(false);
                    archiveTab.setSelected(false);
                    settingsTab.setSelected(true);
                    notifyHandlers(Tab.setting_tab);
                    break;
                case R.id.bug_button:
                    break;
            }
        }
    };
}