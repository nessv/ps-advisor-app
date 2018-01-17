package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.fundacionparaguaya.advisorapp.R;

import java.util.ArrayList;

/**
 * Main side tab for dashboard
 */


public class DashboardTabBarView extends LinearLayout {
    private DashboardTab mFamilyTab;
    private DashboardTab mMapTab;
    private DashboardTab mArchiveTab;
    private DashboardTab mSettingsTab;
    private ImageButton mBugButton;

    public enum DashTab {
        FAMILY,
        MAP,
        ARCHIVE,
        SETTINGS
    }

    private DashboardTab mCurrentlySelected;

    private ArrayList <TabSelectedHandler> tabSelectedHandlers = new ArrayList();

    public DashboardTabBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtabview, this);

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

    protected void selectTab(DashboardTab tab)
    {
        if(mCurrentlySelected!=null)
        {
            mCurrentlySelected.setSelected(false);
        }

        tab.setSelected(true);
        mCurrentlySelected=tab;
    }

    public void addTabSelectedHandle(TabSelectedHandler handler){
        tabSelectedHandlers.add(handler);
    }

    private void notifyHandlers(DashTab tab){
        for(int counter=0; counter < tabSelectedHandlers.size(); counter++){
            tabSelectedHandlers.get(counter).onTabSelection(new TabSelectedEvent(tab));
        }
    }

    final OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()){
                case R.id.family_tab:
                    selectTab(mFamilyTab);

                    notifyHandlers(DashTab.FAMILY);
                    break;
                case R.id.map_tab:
                    selectTab(mMapTab);

                    notifyHandlers(DashTab.MAP);
                    break;
                case R.id.archive_tab:
                    selectTab(mArchiveTab);

                    notifyHandlers(DashTab.ARCHIVE);
                    break;
                case R.id.settings_tab:
                    selectTab(mSettingsTab);

                    notifyHandlers(DashTab.SETTINGS);
                    break;
                case R.id.bug_button:
                    break;
            }
        }
    };

    public interface TabSelectedHandler {
        void onTabSelection(TabSelectedEvent event);
    }

    public class TabSelectedEvent {
        private DashTab selectedTab;
        TabSelectedEvent(DashTab selectedTab){
            this.selectedTab = selectedTab;
        }
        public DashTab getSelectedTab() {
            return selectedTab;
        }
    }
}