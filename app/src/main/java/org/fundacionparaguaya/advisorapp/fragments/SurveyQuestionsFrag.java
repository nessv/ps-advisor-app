package org.fundacionparaguaya.advisorapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import okhttp3.internal.Util;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.SurveyQuestionAdapter;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.BackgroundQuestionCallback;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.SharedSurveyViewModel;

/**
 * Questions about Personal and Economic questions that are asked before the survey
 */

public abstract class SurveyQuestionsFrag extends AbstractSurveyFragment implements BackgroundQuestionCallback {

    //TODO:disable scrolling in recycler view

    static String FRAGMENT_TAG = "SurveyQuestionsFrag";

    protected RecyclerView mDsvQuestionList;
    protected SurveyQuestionAdapter mQuestionAdapter;
    protected SharedSurveyViewModel mSharedSurveyViewModel;

    int mCurrentIndex = 0;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initQuestionList();
    }

    abstract void initQuestionList();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_surveyquestions, container, false);

        mDsvQuestionList = view.findViewById(R.id.rv_survey_questions);

        mDsvQuestionList.setHasFixedSize(true);

        mDsvQuestionList.setAdapter(mQuestionAdapter);

      //  mDsvQuestionList.setSlideOnFling(true);
      //  mDsvQuestionList.setSlideOnFlingThreshold(1800);

        mDsvQuestionList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mDsvQuestionList.setOnTouchListener((v, event)-> false);
       // mDsvQuestionList.setItemTransformer(new SurveyQuestionAdapter.QuestionFadeTransformer());

        mDsvQuestionList.setRecyclerListener((holder) ->
        {
                if(holder instanceof SurveyQuestionAdapter.QuestionViewHolder)
                {
                   SurveyQuestionAdapter.QuestionViewHolder questionHolder=
                           (SurveyQuestionAdapter.QuestionViewHolder)holder;

                    if(questionHolder.itemView.hasFocus())
                    {
                        questionHolder.itemView.clearFocus(); //we can put it inside the second if as well, but it makes sense to do it to all scraped views
                        //Optional: also hide keyboard in that case
                        if ( questionHolder instanceof SurveyQuestionAdapter.TextQuestionViewHolder) {
                            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                }
            }
        );

        /*mDsvQuestionList.addOnItemChangedListener((viewHolder, adapterPosition) -> {
            if(viewHolder!=null)
            {
                viewHolder.itemView.requestFocus();
            }
        });*/

        mQuestionAdapter = new SurveyQuestionAdapter(this);
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


        mDsvQuestionList.smoothScrollToPosition(mCurrentIndex+=1);
        /*
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
        }*/
    }
}
