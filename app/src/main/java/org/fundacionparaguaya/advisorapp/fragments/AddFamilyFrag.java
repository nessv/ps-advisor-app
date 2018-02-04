package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.adapters.BackgroundQuestionAdapter;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.BackgroundQuestionCallback;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.viewmodels.AddFamilyViewModel;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import javax.inject.Inject;
import java.lang.ref.WeakReference;

public class AddFamilyFrag extends AbstractStackedFrag implements BackgroundQuestionCallback {

    private BackgroundQuestionAdapter mBackgroundQuestionAdapter;

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

        mBackgroundQuestionAdapter = new BackgroundQuestionAdapter(this);

        mAddFamilyViewModel.getQuestions().observe(this, (questions) ->
        {
            mBackgroundQuestionAdapter.setQuestionsList(questions);
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

        mDsvQuestionList.setAdapter(mBackgroundQuestionAdapter);

        mDsvQuestionList.setSlideOnFling(true);
        mDsvQuestionList.setSlideOnFlingThreshold(1800);

        mDsvQuestionList.setItemTransformer(new BackgroundQuestionAdapter.QuestionFadeTransformer());

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
    public void onNext(View v) {
        int currentIndex = mDsvQuestionList.getCurrentItem();
        currentIndex++;

        if(currentIndex<mBackgroundQuestionAdapter.getItemCount())
        {
            mDsvQuestionList.smoothScrollToPosition(currentIndex);
        }
    }

    @Override
    public void onFinish() {
        new SaveFamilyAsyncTask(this).execute();
        //get family from view model
        //save it
    }

    static class SaveFamilyAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private WeakReference<AddFamilyFrag> fragReference;

        SaveFamilyAsyncTask(AddFamilyFrag frag)
        {
            fragReference = new WeakReference<AddFamilyFrag>(frag);
        }

        @Override
        protected Void doInBackground (Void ... voids) {
            fragReference.get().mAddFamilyViewModel.saveFamily();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            fragReference.get().navigateBack();
            super.onPostExecute(aVoid);
        }
    }
}
