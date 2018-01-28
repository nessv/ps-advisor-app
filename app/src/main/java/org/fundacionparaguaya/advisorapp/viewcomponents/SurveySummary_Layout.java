package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Created by alex on 1/28/2018.
 */

public class SurveySummary_Layout extends ConstraintLayout {

    Context context;
    AttributeSet attributeSet;

    ImageView layoutIcon;
    TextView layoutTitle;
    TextView numSkipped;
    RecyclerView recyclerView;

    public enum SurveySummaryState{COMPLETE, INCOMPLETE}

    SurveySummaryState state;

    public SurveySummary_Layout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.attributeSet = attrs;

        layoutIcon = (ImageView) findViewById(R.id.surveysummary_icon);
        layoutTitle = (TextView) findViewById(R.id.surveysummary_layout_title);
        numSkipped = (TextView) findViewById(R.id.surveysummary_layout_numskipped);
        recyclerView = (RecyclerView) findViewById(R.id.surveysummary_layout_recyclerview);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SurveySummary_Layout, 0, 0 );

        try {
            layoutTitle.setText(attributes.getResourceId(R.styleable.SurveySummary_Layout_surveysummary_title, R.string.surveysummary_defaulttitle));
        } finally {
            attributes.recycle();
        }
        setState(SurveySummaryState.COMPLETE);
    }

    public void setState(SurveySummaryState state){
        this.state = state;
        switch (state){
            case COMPLETE:
                layoutIcon.setImageDrawable(context.getDrawable(R.drawable.surveysummary_complete));
                numSkipped.setText(" ");
                break;
            case INCOMPLETE:
                layoutIcon.setImageDrawable(context.getDrawable(R.drawable.surveysummary_incomplete));
                break;
            default:
                //do nothing
        }
    }

    public void setNumSkipped(int num){

        numSkipped.setText(num + " " + getResources().getString(R.string.surveysummary_questionsleft));

    }


}
