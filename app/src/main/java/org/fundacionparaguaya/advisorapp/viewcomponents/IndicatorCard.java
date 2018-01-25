package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Default class for a SurveyCard inside of the survey
 * Each survey page will have 3 instances of this
 */

public class IndicatorCard extends CardView{

    private Context context;

    private CardView mSurveyCardSelected;
    private CardView mSurveyCard;
    private SimpleDraweeView mImage;
    private TextView mText;

    public enum CardColor {
        RED, YELLOW, GREEN
    }

    public IndicatorCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.indicator_card, this);

        mSurveyCardSelected = (CardView) findViewById(R.id.survey_card_selected);
        mSurveyCard = (CardView) findViewById(R.id.survey_card_background);
        mImage = (SimpleDraweeView) findViewById(R.id.survey_card_image);
        mText = (TextView) findViewById(R.id.survey_card_text);

        TypedArray attrs = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.IndicatorCard, 0, 0);

        try{
            setColor(attrs.getResourceId(R.styleable.IndicatorCard_indicator_color, R.color.colorPrimaryDark));
            setText(attrs.getResourceId(R.styleable.IndicatorCard_indicator_text, R.string.defaultindicatortext));
            setImage(attrs.getResourceId(R.styleable.IndicatorCard_indicator_image, R.string.family_imagePlaceholder));
        } finally {
            attrs.recycle();
        }

    }

    public void setSelected(boolean isSelected){
        if (isSelected){
            mSurveyCardSelected.setBackgroundColor(context.getColor(R.color.indicator_card_background));
        } else {
            mSurveyCardSelected.setBackground(context.getDrawable(R.drawable.survey_card_background));
        }
    }

    public void setColor(CardColor color){
        switch(color) {
            case RED:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.indicator_card_red));
                break;
            case YELLOW:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.indicator_card_yellow));
                break;
            case GREEN:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.indicator_card_green));
                break;
        }
    }

    public void setColor(int color){
        mSurveyCard.setCardBackgroundColor(color);
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

}