package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by alex on 1/16/2018.
 */

public class DashboardTopBar extends LinearLayout {

    private Context context;
    private AttributeSet attributeSet;

    public DashboardTopBar(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        this.context = context;
        this.attributeSet = attributeSet;
    }

}
