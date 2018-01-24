package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;

/**
 * Created by Mone Elokda on 1/23/2018.
 */

public class QuestionTextView extends LinearLayout implements View.OnClickListener, QuestionViewInterface{

    private TextView mQuestionTextView;
    private EditText mAnswer;

    public QuestionTextView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_questiontext, this);

        mQuestionTextView = (TextView) findViewById(R.id.background_question);
        mAnswer = (EditText) findViewById(R.id.answer_text_field);

    }

    public void setQuestion(PersonalQuestion question)
    {
        String description = question.getDescription();

        switch (question.getResponseType()) {
            case String:
                this.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case Integer:
                this.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                throw new IllegalArgumentException("SurveyQuestion has no valid response type");
        }

    }

    public void setQuestion(EconomicQuestion question)
    {
        String description = question.getDescription();

        switch (question.getResponseType()){
            case String:
                this.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case Integer:
                this.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                throw new IllegalArgumentException("SurveyQuestion has no valid response type");
        }

    }

    @Override
    public void setResponse(String s) {

    }

    public void setInputType(int type)
    {
        mAnswer.setInputType(type);
    }

    public void getResponse(String response)
    {
        mAnswer.setText(response);
    }

    @Override
    public void onClick(View view) {

    }
}
