package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.AddFamilyAdapter;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.QuestionResponseListener;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.viewcomponents.QuestionViewInterface;
import org.fundacionparaguaya.advisorapp.viewmodels.AddFamilyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class AddFamilyFrag extends StackedFrag implements QuestionResponseListener {

    private AddFamilyAdapter mAddFamilyAdapter;

    private static String NEW_FAMILY_KEY = "SELECTED_FAMILY";

    private static float MIN_QUESTION_OPACITY = 0.3f;

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    AddFamilyViewModel mAddFamilyViewModel;
    DiscreteScrollView mDsvQuestionList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mAddFamilyViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(AddFamilyViewModel.class);

        mAddFamilyAdapter = new AddFamilyAdapter(this);

        mAddFamilyViewModel.getQuestions().observe(this, (questions) ->
        {
            mAddFamilyAdapter.setQuestionsList(questions);
        });


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.addfamily_frag, container, false);

        mDsvQuestionList = (DiscreteScrollView) view.findViewById(R.id.addfaily_questions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
        mDsvQuestionList.setHasFixedSize(true);

        mDsvQuestionList.setAdapter(mAddFamilyAdapter);

        mDsvQuestionList.setSlideOnFling(true);
        mDsvQuestionList.setSlideOnFlingThreshold(1800);

        mDsvQuestionList.setItemTransformer(new QuestionFadeTransformer());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onQuestionAnswered(BackgroundQuestion q, Object response) {
        mAddFamilyViewModel.addFamilyResponse(q, response);
    }

    @Override
    public void onFamilyAdded(Family family) {

    }


    /**Fades the questions that are not centered in the Discrete Scroll View**/
    public class QuestionFadeTransformer implements DiscreteScrollItemTransformer
    {
        @Override
        public void transformItem(View item, float position) {
            //pos inbetween -1 and 1, inclusive

            //first normalize so between 0 and 1
            //1 is max value

            float absPosition = Math.abs(position);
            absPosition = 1-absPosition; //flip value.. so 1 is max

            //we want to scale 0->1 to .3->1
            //0 -> .3
            //1 -> 1
            // (0.7)(x) + 0.3

            float output = 0.7f * (absPosition) + 0.2f; //inbetween 100% and 30% output

            item.setAlpha(output);
        }
    }

}
