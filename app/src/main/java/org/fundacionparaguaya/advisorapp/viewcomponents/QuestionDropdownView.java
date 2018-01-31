package org.fundacionparaguaya.advisorapp.viewcomponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;

import java.util.List;


public class QuestionDropdownView extends LinearLayout implements View.OnClickListener, QuestionViewInterface {

    private TextView mQuestionTextView;
    private Spinner mAnswerSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;

    public QuestionDropdownView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_bkgquestion_dropdown, this);

        mQuestionTextView = (TextView) findViewById(R.id.background_question);
        mAnswerSpinner = (Spinner) findViewById(R.id.dropdown_field);
    }

    public void setQuestion(BackgroundQuestion question) {

        String description = question.getDescription();

        mQuestionTextView.setText(description);

        if(question.getOptions() != null){
            createAdapter(question.getOptions());
        } else {
            throw new IllegalArgumentException("This question has no options");
        }
    }

    public void addOnSelectionHandler(AdapterView.OnItemSelectedListener listener)
    {
        mAnswerSpinner.setOnItemSelectedListener(listener);
    }

    private void createAdapter(List<String> options)
    {
        mSpinnerAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, options);

        mAnswerSpinner.setAdapter(mSpinnerAdapter);
    }

    @Override
    public void setResponse(String s) {
        //TODO: Validate this response (make sure it is valid option @benhylak)
        mAnswerSpinner.setSelection(mSpinnerAdapter.getPosition(s));
    }

    @Override
    public void onClick(View view) {

    }
}
