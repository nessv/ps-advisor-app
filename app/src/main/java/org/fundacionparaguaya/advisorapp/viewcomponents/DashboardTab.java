package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
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
    }

    public DashboardTab(Context context, AttributeSet attributeSet, int imageID, String title){
        super(context, attributeSet);
        image_icon = (ImageView) findViewById(R.id.imageView_icon);
        textView_caption = (TextView) findViewById(R.id.textView_caption);
        setImage(imageID);
        setText(title);
    }

    public void setSelected(boolean selected){

    }

    public void setImage(int id) {
        image_icon.setImageResource(id);
    }

    public void setText(String caption){
        textView_caption.setText(caption);
    }

}