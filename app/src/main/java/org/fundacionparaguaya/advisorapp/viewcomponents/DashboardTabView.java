package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Main side tab for dashboard
 */

public class DashboardTabView extends LinearLayout {
    public DashboardTabView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtabview, this);
    }


}