package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Created by alex on 1/13/2018.
 */

public class DashboardTab extends LinearLayout {

    private ImageView image_icon;
    private TextView textView_caption;

    public DashboardTab(Context context, AttributeSet attr){
        super(context, attr);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtab, this);

        image_icon = (ImageView) findViewById(R.id.imageView_icon);
        textView_caption = (TextView) findViewById(R.id.textView_caption);

        TypedArray a = context.getTheme().obtainStyledAttributes(attr, R.styleable.DashboardTab,0, 0);

        try {
            image_icon.setImageResource(a.getResourceId(R.styleable.DashboardTab_ImageID, R.drawable.dashtab_friendsicon));
            textView_caption.setText(a.getResourceId(R.styleable.DashboardTab_TextID, R.string.family_tab));
        } finally {
            a.recycle();
        }

    }

    public void setImage(int id) {
        image_icon.setImageResource(id);
    }

    public void setText_string(String caption){
        textView_caption.setText(caption);
    }

    public void setText(int id){
        textView_caption.setText(id);
    }

}