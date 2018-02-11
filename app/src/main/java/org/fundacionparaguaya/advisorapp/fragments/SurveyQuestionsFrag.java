package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.BackgroundQuestionAdapter;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.BackgroundQuestionCallback;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Questions about Personal and Economic questions that are asked before the survey
 */

public class SurveyQuestionsFrag extends AbstractSurveyFragment implements BackgroundQuestionCallback {

    static String FRAGMENT_TAG = "SurveyQuestionsFrag";

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    SharedSurveyViewModel mSharedSurveyViewModel;

    protected DiscreteScrollView mDsvQuestionList;
    protected BackgroundQuestionAdapter mQuestionAdapter;

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

        setTitle(getString(R.string.survey_bkgquestions_title));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initQuestionList();
    }

    protected void initQuestionList()
    {
        Survey survey = mSharedSurveyViewModel.getSurveyInProgress();
        List<BackgroundQuestion> questions = new ArrayList<>(
                survey.getPersonalQuestions().size() + survey.getEconomicQuestions().size());
        questions.addAll(survey.getPersonalQuestions());
        questions.addAll(survey.getEconomicQuestions());

        mQuestionAdapter.setQuestionsList(questions);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surveyquestions, container, false);

        mDsvQuestionList = view.findViewById(R.id.rv_survey_questions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//      ,recyclerView.setLayoutManager(layoutManager);
        mDsvQuestionList.setHasFixedSize(true);

        mDsvQuestionList.setAdapter(mQuestionAdapter);

      //  mDsvQuestionList.setSlideOnFling(true);
      //  mDsvQuestionList.setSlideOnFlingThreshold(1800);

        mDsvQuestionList.setItemTransformer(new BackgroundQuestionAdapter.QuestionFadeTransformer());

        mDsvQuestionList.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if(holder instanceof BackgroundQuestionAdapter.QuestionViewHolder)
                {
                   BackgroundQuestionAdapter.QuestionViewHolder questionHolder=
                           (BackgroundQuestionAdapter.QuestionViewHolder)holder;

                    if(questionHolder.itemView.hasFocus())
                    {
                        questionHolder.itemView.clearFocus(); //we can put it inside the second if as well, but it makes sense to do it to all scraped views
                        //Optional: also hide keyboard in that case
                        if ( questionHolder instanceof BackgroundQuestionAdapter.TextQuestionViewHolder) {
                            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }
            }
        });


        mDsvQuestionList.addOnItemChangedListener((viewHolder, adapterPosition) -> {
            if(viewHolder!=null)
            {
                viewHolder.itemView.requestFocus();
            }
        });

        mQuestionAdapter = new BackgroundQuestionAdapter(this);
        mDsvQuestionList.setAdapter(mQuestionAdapter);

        return view;
    }

    @Override
    public void onQuestionAnswered(BackgroundQuestion q, Object response) {
        try {
            //all responses to questions (for now) should be strings
            mSharedSurveyViewModel.addBackgroundResponse(q, (String)response);
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void onNext(View v) {
        int currentIndex = mDsvQuestionList.getCurrentItem();
        currentIndex++;

        View currentFocus;

        if(getActivity()!=null && (currentFocus=getActivity().getCurrentFocus())!=null)
        {
            currentFocus.clearFocus();
        }

        if(currentIndex< mQuestionAdapter.getItemCount())
        {
            mDsvQuestionList.smoothScrollToPosition(currentIndex);
        }
    }

    @Override
    public void onFinish() {
        //should check if all required questions have been answered before transitioning
        mSharedSurveyViewModel.setSurveyState(SharedSurveyViewModel.SurveyState.INDICATORS);
    }
}
