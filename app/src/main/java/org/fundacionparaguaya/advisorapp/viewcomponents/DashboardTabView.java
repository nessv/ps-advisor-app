package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Main side tab for dashboard
 */



public class DashboardTabView extends LinearLayout {
    LinearLayout dashboardTabView;
    ImageView fpLogo;
    DashboardTab familyTab;
    DashboardTab mapTab;
    DashboardTab archiveTab;
    DashboardTab settingsTab;
    ImageButton bugButton;
    public DashboardTabView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        dashboardTabView = (LinearLayout) findViewById(R.id.dashboardtabview);
        fpLogo = (ImageView) findViewById(R.id.fp_logo);
        familyTab = (DashboardTab) findViewById(R.id.family_tab);
        mapTab = (DashboardTab) findViewById(R.id.map_tab);
        archiveTab = (DashboardTab) findViewById(R.id.archive_tab);
        settingsTab = (DashboardTab) findViewById(R.id.settings_tab);
        bugButton = (ImageButton) findViewById(R.id.bug_button);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtabview, this);

    }


}