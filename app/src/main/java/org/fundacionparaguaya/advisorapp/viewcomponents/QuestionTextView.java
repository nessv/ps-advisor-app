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

public class QuestionTextView extends LinearLayout implements View.OnClickListener{

    private TextView mQuestionTextView;
    private EditText mAnswer;

    public QuestionTextView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_questiontext, this);

        mQuestionTextView = (TextView) findViewById(R.id.background_question);
        mAnswer = (EditText) findViewById(R.id.answer_text_field);

        this.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void setQuestion(PersonalQuestion question)
    {
        String description = question.getDescription();

    }

    public void setQuestion(EconomicQuestion question)
    {

    }

    public void setInputType(int type)
    {
        mAnswer.setInputType(type);
    }

    public void getResponse(String response)
    {

    }

    @Override
    public void onClick(View view) {

    }
}
