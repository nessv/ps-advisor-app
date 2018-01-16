package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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

    private Context context;
    private AttributeSet attributeSet;

    private ImageView image_icon;
    private TextView textView_caption;
    private LinearLayout linearLayout;

    public DashboardTab(Context context, AttributeSet attr){
        super(context, attr);

        this.context = context;
        this.attributeSet = attr;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtab, this);

        image_icon = (ImageView) findViewById(R.id.imageView_icon);
        textView_caption = (TextView) findViewById(R.id.textView_caption);
        linearLayout = (LinearLayout) findViewById(R.id.dashboardtab);

        //Find custom xml attributes and apply them
        TypedArray a = context.getTheme().obtainStyledAttributes(attr, R.styleable.DashboardTab,0, 0);
        try {
            image_icon.setImageResource(a.getResourceId(R.styleable.DashboardTab_ImageID, R.drawable.dashtab_friendsicon)); //set image to icon
            textView_caption.setText(a.getResourceId(R.styleable.DashboardTab_TextID, R.string.family_tab));                //set caption text
            linearLayout.setBackgroundResource(R.color.tabNotSelected);                                                     //set default background
            setSelected(a.getBoolean(R.styleable.DashboardTab_Default, false));                                     //set default selected
        } finally {
            a.recycle();
        }

    }

    public void setSelected(boolean isSelected){
        if (isSelected) {
            linearLayout.setBackgroundResource(R.color.tabSelected);//Change Tab Background
            image_icon.setColorFilter(new PorterDuffColorFilter(context.getColor(R.color.iconSelected), PorterDuff.Mode.MULTIPLY));//Change Icon Color

            textView_caption.setTextColor(context.getColor(R.color.captionSelected));//Change Text Color
        } else {
            linearLayout.setBackgroundResource(R.color.tabNotSelected);//Change Tab Background
            image_icon.setColorFilter(R.color.iconNotSelected);//Change Icon Color
            textView_caption.setTextColor(getResources().getColor(R.color.captionNotSelected));//Change Text Color
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