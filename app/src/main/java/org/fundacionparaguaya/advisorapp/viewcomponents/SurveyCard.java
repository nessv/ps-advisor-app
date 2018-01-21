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

import org.fundacionparaguaya.advisorapp.R;

/**
 * Default class for a SurveyCard inside of the survey
 * Each survey page will have 3 instances of this
 */

public class SurveyCard extends CardView{

    private Context context;

    private CardView mSurveyCardSelected;
    private CardView mSurveyCard;
    private ImageView mImage;
    private TextView mText;

    public enum CardColor {
        RED, YELLOW, GREEN
    }

    public SurveyCard(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.survey_card, this);

        mSurveyCardSelected = (CardView) findViewById(R.id.survey_card_selected);
        mSurveyCard = (CardView) findViewById(R.id.survey_card_background);
        mImage = (ImageView) findViewById(R.id.survey_card_image);
        mText = (TextView) findViewById(R.id.survey_card_text);

        TypedArray attrs = context.getTheme().obtainStyledAttributes(attr, R.styleable.SurveyCard, 0, 0);

        try{
            setColor(attrs.getResourceId(R.styleable.SurveyCard_color, R.color.survey_card_green));
            setText(attrs.getResourceId(R.styleable.SurveyCard_text, NO_ID));
            setImage(attrs.getResourceId(R.styleable.SurveyCard_image, NO_ID));
        } finally {
            attrs.recycle();
        }

    }

    public void setSelected(boolean isSelected){
        if (isSelected){
            mSurveyCardSelected.setBackgroundColor(context.getColor(R.color.survey_card_background));
        } else {
            mSurveyCardSelected.setBackground(context.getDrawable(R.drawable.survey_card_background));
        }
    }

    public void setColor(CardColor color){
        switch(color) {
            case RED:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.survey_card_red));
                break;
            case YELLOW:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.survey_card_yellow));
                break;
            case GREEN:
                mSurveyCard.setCardBackgroundColor(context.getColor(R.color.survey_card_green));
                break;
        }
    }

    public void setColor(int color){
        mSurveyCard.setCardBackgroundColor(color);
    }

    public void setImage(Drawable drawable){
        mImage.setImageDrawable(drawable);
    }

    public void setImage(Uri uri){
        mImage.setImageURI(uri);
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