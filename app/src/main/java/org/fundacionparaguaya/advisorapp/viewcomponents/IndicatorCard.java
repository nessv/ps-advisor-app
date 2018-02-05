package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;

/**
 * Default class for a SurveyCard inside of the survey
 * Each survey page will have 3 instances of this
 */

public class IndicatorCard extends LinearLayout{

    private Context context;

    private CardView mIndicatorBackground;
    private CardView mIndicatorCard;
    private SimpleDraweeView mImage;
    private TextView mText;

    private ViewTreeObserver observer;

    private IndicatorOption mIndicatorOption;

    public enum CardColor {
        RED, YELLOW, GREEN
    }

    public IndicatorCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.indicator_card, this, true);

        mIndicatorBackground = findViewById(R.id.survey_card_selected);
        mIndicatorCard = (CardView) findViewById(R.id.survey_card_background);
        mImage = (SimpleDraweeView) findViewById(R.id.survey_card_image);
        mText = (TextView) findViewById(R.id.survey_card_text);

        mText.setMovementMethod(new ScrollingMovementMethod());

        TypedArray attrs = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IndicatorCard, 0, 0);

        //When view is created, resize the textview
        observer = mText.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                resize();
            }
        });

        try{
            setColor(attrs.getResourceId(R.styleable.IndicatorCard_indicator_color, Color.TRANSPARENT));
            setText(attrs.getResourceId(R.styleable.IndicatorCard_indicator_text, R.string.defaultindicatortext));
            setImage(attrs.getResourceId(R.styleable.IndicatorCard_indicator_image, R.string.family_imagePlaceholder));
        } finally {
            attrs.recycle();
        }
    }

    public void resize(){
        mText.setMaxHeight(mIndicatorCard.getHeight() - convertDpToPixel(17)*3 - convertDpToPixel(150));
    }

    public void setOption(IndicatorOption option)
    {
        mIndicatorOption = option;

        this.setImage(Uri.parse(option.getImageUrl()));
        this.setText(option.getDescription());
    }

    public IndicatorOption getOption()
    {
        return mIndicatorOption;
    }

    public void setSelected(boolean isSelected){
        if (isSelected){
              mIndicatorBackground.setCardBackgroundColor(context.getColor(R.color.indicator_card_selected));
        } else {
              mIndicatorBackground.setCardBackgroundColor(context.getColor(android.R.color.transparent));
        }
    }

    public void setColor(CardColor color){
        switch(color) {
            case RED:
                setColor(R.color.indicator_card_red);
                break;
            case YELLOW:
                setColor(R.color.indicator_card_yellow);
                break;
            case GREEN:
                setColor(R.color.indicator_card_green);
                break;
        }
    }

    public void setColor(int color){
        mIndicatorCard.setCardBackgroundColor(context.getColor(color));
    }

    public void setImage(Uri uri){
        mImage.setImageURI(uri, context);
    }

    public void setImage(int image){
        mImage.setImageURI(Uri.parse(getResources().getString(image)));
    }


    public void setText(int id) {
        mText.setText(id);
    }

    public void setText(String text){
        mText.setText(text);
    }

    public static int convertDpToPixel(int dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) Math.round(px);
    }

}