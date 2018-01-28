package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.viewcomponents.QuestionDropdownView;
import org.fundacionparaguaya.advisorapp.viewcomponents.QuestionTextView;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import javax.inject.Inject;

/**
 * Questions about Personal and Economic questions that are asked before the survey
 */

public class SurveyQuestionsFrag extends AbstractSurveyFragment {

    static String FRAGMENT_TAG = "SurveyQuestionsFrag";

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSharedSurveyViewModel;

    LinearLayout mQuestionContainer;
    Button mSubmitButton;

    public SurveyQuestionsFrag()
    {
        super();

        //sets colors for parent activity (set by parent activity in SurveyActivity.switchSurveyFrag)
        setFooterColor(R.color.survey_darkyellow);
        setHeaderColor(R.color.survey_darkyellow);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mSharedSurveyViewModel = ViewModelProviders.
                 of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(SharedSurveyViewModel.class);

        setTitle("Background Questions");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for(PersonalQuestion q : mSharedSurveyViewModel.getSurveyInProgress().getPersonalQuestions())
        {
            if(q.getOptions() != null && q.getOptions().size()>0) //if it has options, it calls for a drop down
            {
                QuestionDropdownView view = new QuestionDropdownView(getContext());

                view.setQuestion(q);
                view.setResponse(mSharedSurveyViewModel.getBackgroundResponse(q));

                view.addOnSelectionHandler(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String s = (String)adapterView.getItemAtPosition(i);
                        mSharedSurveyViewModel.addBackgroundResponse(q, s);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //add listener to update view model when value is given
                mQuestionContainer.addView(view);
            }
            else
            {
                QuestionTextView view = new QuestionTextView(getContext());

                view.setQuestion(q);
                view.setResponse(mSharedSurveyViewModel.getBackgroundResponse(q));
                view.responseTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String s = view.getResponse();
                        mSharedSurveyViewModel.addBackgroundResponse(q, s);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                //add listener to update view model when value is given
                mQuestionContainer.addView(view);
            }
        }

        for(EconomicQuestion q : mSharedSurveyViewModel.getSurveyInProgress().getEconomicQuestions())
        {
            if(q.getOptions()!=null && q.getOptions().size()>0) //if it has options, it calls for a drop down
            {
                QuestionDropdownView view = new QuestionDropdownView(getContext());

                view.setQuestion(q);
                view.setResponse(mSharedSurveyViewModel.getBackgroundResponse(q));

                view.addOnSelectionHandler(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String s = (String)adapterView.getItemAtPosition(i);
                        mSharedSurveyViewModel.addBackgroundResponse(q, s);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //add listener to update view model when value is given
                mQuestionContainer.addView(view);
            }
            else
            {
                QuestionTextView view = new QuestionTextView(getContext());

                view.setQuestion(q);
                view.setResponse(mSharedSurveyViewModel.getBackgroundResponse(q));

                view.responseTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String s = view.getResponse();
                        mSharedSurveyViewModel.addBackgroundResponse(q, s);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                //add listener to update view model when value is given

                mQuestionContainer.addView(view);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bkgquestions, container, false);

        mQuestionContainer = v.findViewById(R.id.linearlayout_bkgquestions_container);
        mSubmitButton = v.findViewById(R.id.btn_bkgquestions_continue);

        mSubmitButton.setOnClickListener((event)->
        {
            //TODO: should check if all required questions are completed
            mSharedSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.INDICATORS);
        });

        return v;
    }
}
