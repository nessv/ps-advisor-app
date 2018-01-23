package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;

/**
 * Created by Mone Elokda on 1/23/2018.
 */

public class BackgroundQuestionsView extends LinearLayout implements View.OnClickListener{

    private TextView mQuestion;
    private EditText mAnswer;



    public BackgroundQuestionsView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.fragment_question_background, this);

        mQuestion = (TextView) findViewById(R.id.background_question);
        mAnswer = (EditText) findViewById(R.id.answer_text_field);

        //mAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT | );
    }

    @Override
    public void onClick(View view) {

    }
}
