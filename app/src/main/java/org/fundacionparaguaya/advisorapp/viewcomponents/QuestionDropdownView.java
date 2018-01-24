package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;

import java.util.List;

/**
 * Created by Mone Elokda on 1/23/2018.
 */

public class QuestionDropdownView extends LinearLayout implements View.OnClickListener, QuestionViewInterface {

    private TextView mQuestionTextView;
    private QuestionDropdownView mAnswer;

    public QuestionDropdownView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_questiondropdown, this);

        mQuestionTextView = (TextView) findViewById(R.id.background_question);
        mAnswer = (QuestionDropdownView) findViewById(R.id.dropdown_field);
    }

    public void setQuestion(PersonalQuestion question) {

        String description = question.getDescription();

        if(question.getOptions() != null){

            List<String> options = question.getOptions();

        } else {
            throw new IllegalArgumentException("This question has no options");
        }
    }

    public void setQuestion(EconomicQuestion question){

        String description = question.getDescription();

        if(question.getOptions() != null){

            List<String> options = question.getOptions();

        } else {
            throw new IllegalArgumentException("This question has no options");
        }
    }

    @Override
    public void setResponse(String s) {
        mAnswer.setResponse(s);
    }

    @Override
    public void onClick(View view) {

    }
}
