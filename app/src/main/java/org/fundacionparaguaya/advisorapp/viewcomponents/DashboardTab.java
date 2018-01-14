package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Created by alex on 1/13/2018.
 */

public class DashboardTab extends LinearLayoutCompat {

    ImageView image_icon;
    TextView textView_caption;

    public DashboardTab(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        TypedArray a = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.DashboardTab,0, 0);

        setImage(a.getResourceId(R.styleable.DashboardTab_ImageID, 0)); //TODO: change default values
        setText(a.getResourceId(R.styleable.DashboardTab_TextID, 0));
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