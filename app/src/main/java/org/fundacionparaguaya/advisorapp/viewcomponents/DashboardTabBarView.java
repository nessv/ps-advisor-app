package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.fundacionparaguaya.advisorapp.R;

import java.util.ArrayList;

/**
 * Main side tab for dashboard
 */


public class DashboardTabBarView extends LinearLayout {
    private LinearLayout mDashboardTabView;
    private ImageView mFPLogo;
    private DashboardTab mFamilyTab;
    private DashboardTab mMapTab;
    private DashboardTab mArchiveTab;
    private DashboardTab mSettingsTab;
    private ImageButton mBugButton;

    public enum Tab{
        FAMILY,
        MAP,
        ARCHIVE,
        SETTINGS
    }

    private ArrayList <TabSelectedHandler> tabSelectedHandlers = new ArrayList();


    public DashboardTabBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtabview, this);

        mDashboardTabView = (LinearLayout) findViewById(R.id.dashboardtabview);
        mFPLogo = (ImageView) findViewById(R.id.fp_logo);
        mFamilyTab = (DashboardTab) findViewById(R.id.family_tab);
        mMapTab = (DashboardTab) findViewById(R.id.map_tab);
        mArchiveTab = (DashboardTab) findViewById(R.id.archive_tab);
        mSettingsTab = (DashboardTab) findViewById(R.id.settings_tab);
        mBugButton = (ImageButton) findViewById(R.id.bug_button);

        mFamilyTab.setOnClickListener(clickListener);
        mMapTab.setOnClickListener(clickListener);
        mArchiveTab.setOnClickListener(clickListener);
        mSettingsTab.setOnClickListener(clickListener);

        mBugButton.setOnClickListener(clickListener);
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
                    mFamilyTab.setSelected(true);
                    mMapTab.setSelected(false);
                    mArchiveTab.setSelected(false);
                    mSettingsTab.setSelected(false);
                    notifyHandlers(Tab.FAMILY);
                    break;
                case R.id.map_tab:
                    mFamilyTab.setSelected(false);
                    mMapTab.setSelected(true);
                    mArchiveTab.setSelected(false);
                    mSettingsTab.setSelected(false);
                    notifyHandlers(Tab.MAP);
                    break;
                case R.id.archive_tab:
                    mFamilyTab.setSelected(false);
                    mMapTab.setSelected(false);
                    mArchiveTab.setSelected(true);
                    mSettingsTab.setSelected(false);
                    notifyHandlers(Tab.ARCHIVE);
                    break;
                case R.id.settings_tab:
                    mFamilyTab.setSelected(false);
                    mMapTab.setSelected(false);
                    mArchiveTab.setSelected(false);
                    mSettingsTab.setSelected(true);
                    notifyHandlers(Tab.SETTINGS);
                    break;
                case R.id.bug_button:
                    break;
            }
        }
    };
}