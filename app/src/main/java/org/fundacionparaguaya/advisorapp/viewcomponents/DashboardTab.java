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
 * Custom Tab for the DashboardTabBarView
 */

public class DashboardTab extends LinearLayout {

    private Context context;
    private AttributeSet attributeSet;

    private ImageView mImageIcon;
    private TextView mTextViewCaption;
    private LinearLayout mTabLayout;

    public DashboardTab(Context context, AttributeSet attr){
        super(context, attr);

        this.context = context;
        this.attributeSet = attr;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.dashboardtab, this);

        mImageIcon = (ImageView) findViewById(R.id.imageView_icon);
        mTextViewCaption = (TextView) findViewById(R.id.textView_caption);
        mTabLayout = (LinearLayout) findViewById(R.id.dashboardtab);

        //Find custom xml attributes and apply them
        TypedArray a = context.getTheme().obtainStyledAttributes(attr, R.styleable.DashboardTab,0, 0);
        try {
            mImageIcon.setImageResource(a.getResourceId(R.styleable.DashboardTab_imageID, R.drawable.dashtab_friendsicon)); //set image to icon
            mTextViewCaption.setText(a.getResourceId(R.styleable.DashboardTab_textID, R.string.family_tab));                //set caption text
            mTabLayout.setBackgroundResource(R.color.tabNotSelected);                                                     //set default background
            setSelected(a.getBoolean(R.styleable.DashboardTab_defaultBool, false));                                     //set default selected
        } finally {
            a.recycle();
        }

    }

    public void setSelected(boolean isSelected){
        if (isSelected) {
            mTabLayout.setBackgroundResource(R.color.tabSelected);//Change Tab Background
            mImageIcon.setColorFilter(new PorterDuffColorFilter(context.getColor(R.color.iconSelected), PorterDuff.Mode.MULTIPLY));//Change Icon Color

            mTextViewCaption.setTextColor(context.getColor(R.color.captionSelected));//Change Text Color
        } else {
            mTabLayout.setBackgroundResource(R.color.tabNotSelected);//Change Tab Background
            mImageIcon.setColorFilter(R.color.iconNotSelected);//Change Icon Color
            mTextViewCaption.setTextColor(getResources().getColor(R.color.captionNotSelected));//Change Text Color
        }
    }

    public void setImage(int id) {
        mImageIcon.setImageResource(id);
    }

    public void setText_string(String caption){
        mTextViewCaption.setText(caption);
    }

    public void setText(int id){
        mTextViewCaption.setText(id);
    }

}