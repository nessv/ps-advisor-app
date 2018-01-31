package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;


public class QuestionTextView extends LinearLayout implements View.OnClickListener, QuestionViewInterface{

    private TextView mQuestionTextView;
    private EditText mAnswer;

    public QuestionTextView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_bkgquestion_text, this);

        mQuestionTextView = (TextView) findViewById(R.id.background_question);
        mAnswer = (EditText) findViewById(R.id.answer_text_field);
    }

    public void setQuestion(BackgroundQuestion question)
    {
        String description = question.getDescription();

        mQuestionTextView.setText(description);

        switch (question.getResponseType()) {
            case STRING:
                this.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case INTEGER:
                this.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;

            default:
                throw new IllegalArgumentException("SurveyQuestion has no valid response type");
        }

    }

    @Override
    public void setResponse(String s) {

    }

    public void responseTextChangedListener(TextWatcher watcher){

        mAnswer.addTextChangedListener(watcher);

    }

    private void setInputType(int type)
    {
        mAnswer.setInputType(type);
    }

    public String getResponse()
    {
        return mAnswer.getText().toString();
    }

    @Override
    public void onClick(View view) {

    }
}
