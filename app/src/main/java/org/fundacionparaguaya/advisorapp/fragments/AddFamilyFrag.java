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

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.QuestionResponseListener;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
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

    @Inject
    InjectionViewModelFactory mViewModelFactory;
    AddFamilyViewModel mAddFamilyViewModel;

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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.addfaily_questions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mAddFamilyAdapter);

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


    private static class AddFamilyAdapter extends RecyclerView.Adapter {

        int STRING_INPUT = 1;
        int LOCATION_INPUT = 2;
        int PHOTO_INPUT = 3;

        List<BackgroundQuestion> mQuestionsList;
        QuestionResponseListener mQuestionResponseListener;

        public AddFamilyAdapter(QuestionResponseListener listener){
            mQuestionResponseListener = listener;
        }

        public void setQuestionsList(List<BackgroundQuestion> questionsList)
        {
            mQuestionsList = questionsList;
            notifyDataSetChanged();
        }


        @Override
        public int getItemViewType(int position) {
            BackgroundQuestion question = mQuestionsList.get(position);
            switch (question.getResponseType()){
                case STRING:
                case PHONE_NUMBER:
                    return  STRING_INPUT;

                case PHOTO:
                    return PHOTO_INPUT;

                case LOCATION:
                    return LOCATION_INPUT;
                default:
                    return -1;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            if (viewType == STRING_INPUT){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfamily_textquestion, parent, false);
                 return new TextQuestionViewHolder(view);
            }
            else if (viewType == LOCATION_INPUT){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfamily_locationquestion, parent, false);
                return new LocationViewHolder(view);
            }
            else if (viewType == PHOTO_INPUT){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfamily_picturequestion, parent, false);
                return new PictureViewHolder(view);
            }
            else {
                return null;
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            try
            {
                QuestionViewHolder questionViewHolder = (QuestionViewHolder)holder;
                questionViewHolder.setQuestion(mQuestionsList.get(position));
                questionViewHolder.setQuestionResponseListener(mQuestionResponseListener);
            }
            catch (ClassCastException e)
            {
                Log.e("", e.getMessage());
            }
        }


        @Override
        public int getItemCount() {
            return mQuestionsList.size();
        }


        public static class TextQuestionViewHolder extends QuestionViewHolder {


            LinearLayout familyInfoItem;
            TextView familyInfoQuestion;
            EditText familyInfoEntry;

            public TextQuestionViewHolder(View itemView) {
                super(itemView);
                familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_textquestion);
                familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_textquestion);
                familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_text_field);

                familyInfoEntry.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String answer = familyInfoEntry.getText().toString();
                        mQuestionResponseListener.onQuestionAnswered(mQuestion, answer);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

            @Override
            public void setQuestion(BackgroundQuestion question) {
                mQuestion = question;
                familyInfoQuestion.setText(question.getDescription());
            }

        }

        public static class LocationViewHolder extends QuestionViewHolder{

            LinearLayout familyInfoItem;
            TextView familyInfoQuestion;
            EditText familyInfoEntry;

            public LocationViewHolder(View itemView) {
                super(itemView);

                familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_textquestion);
                familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_locationquestion);
                familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_location_field);

            }

            @Override
            public void setQuestion(BackgroundQuestion question) {
                mQuestion = question;
                familyInfoQuestion.setText(question.getDescription());

            }


            public String onResponse()
            {
                return familyInfoEntry.getText().toString();
            }

        }

        public static class PictureViewHolder extends QuestionViewHolder{

            LinearLayout familyInfoItem;
            TextView familyInfoQuestion;
            ImageButton cameraButton;
            ImageButton galleryButton;
            ImageView responsePicture;


            public PictureViewHolder(View itemView) {
                super(itemView);
                familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_picturequestion);
                familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_picturequestion);
                cameraButton = (ImageButton) itemView.findViewById(R.id.camera_button);
                galleryButton = (ImageButton) itemView.findViewById(R.id.gallery_button);

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        itemView.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void setQuestion(BackgroundQuestion question) {
                mQuestion = question;
                familyInfoQuestion.setText(question.getDescription());

            }

            public void onResponse()
            {
                responsePicture.setVisibility(View.VISIBLE);
                cameraButton.setVisibility(View.INVISIBLE);
                galleryButton.setVisibility(View.INVISIBLE);
            }

        }

        abstract static class QuestionViewHolder extends RecyclerView.ViewHolder
        {
            protected BackgroundQuestion mQuestion;
            QuestionResponseListener mQuestionResponseListener;

            public QuestionViewHolder(View itemView) {
                super(itemView);
            }

            public void setQuestionResponseListener(QuestionResponseListener listener)
            {
                mQuestionResponseListener = listener;
            }

            public abstract void setQuestion(BackgroundQuestion question);
        }


    }

}
