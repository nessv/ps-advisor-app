package org.fundacionparaguaya.advisorapp.adapters;

import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.BackgroundQuestionCallback;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;

import java.util.List;

public class SurveyQuestionAdapter extends RecyclerView.Adapter {

    final static int STRING_INPUT = 1;
    final static int LOCATION_INPUT = 2;
    final static int PHOTO_INPUT = 3;
    final static int DROPDOWN_INPUT =4; //TODO: implement dropdowns
    final static int REVIEW_PAGE = 5;

    List<BackgroundQuestion> mQuestionsList;
    BackgroundQuestionCallback mBackgroundQuestionCallback;

    public SurveyQuestionAdapter(BackgroundQuestionCallback listener){
        mBackgroundQuestionCallback = listener;
    }

    public void setQuestionsList(List<BackgroundQuestion> questionsList)
    {
        mQuestionsList = questionsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if(position == mQuestionsList.size()){
            return REVIEW_PAGE;
        }
        else {
            BackgroundQuestion question = mQuestionsList.get(position);

            if (question.getOptions() != null && question.getOptions().size() > 1) {
                return DROPDOWN_INPUT;
            }
            else {
                switch (question.getResponseType()) {
                    case STRING:
                    case PHONE_NUMBER:
                    case INTEGER:
                        return STRING_INPUT;

                    case PHOTO:
                        return PHOTO_INPUT;

                    case LOCATION:
                        return LOCATION_INPUT;

                    default:
                        return -1;
                }
            }
        }
    }

    //inflate the correct type of question view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView;
        RecyclerView.ViewHolder vh = null;

        switch (viewType)
        {
            case STRING_INPUT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questiontext, parent, false);
                vh = new TextQuestionViewHolder(mBackgroundQuestionCallback, itemView);
                break;

            case LOCATION_INPUT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionlocation, parent, false);
                vh = new LocationViewHolder(mBackgroundQuestionCallback, itemView);
                break;

            case PHOTO_INPUT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionphoto, parent, false);
                vh = new PictureViewHolder(mBackgroundQuestionCallback, itemView);
                break;

            case DROPDOWN_INPUT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questiondropdown, parent, false);
                vh = new DropdownViewHolder(mBackgroundQuestionCallback, itemView);
                break;

            case REVIEW_PAGE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionsreview, parent, false);
                vh = new ReviewViewHolder(mBackgroundQuestionCallback, itemView);
                break;
        }

        if(vh==null)
        {
            throw new IllegalArgumentException("View holder was not a valid type for background questions");
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try
        {
            QuestionViewHolder questionViewHolder = (QuestionViewHolder)holder
                    ;
            questionViewHolder.setQuestion(mQuestionsList.get(position));
            questionViewHolder.setBackgroundQuestionCallback(mBackgroundQuestionCallback);

            if(getItemViewType(position) == REVIEW_PAGE)
            {
                ((ReviewViewHolder)holder).setQuestions(mQuestionsList);
            }
        }
        catch (ClassCastException e)
        {
            Log.e("", e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        if(mQuestionsList == null) return 0; //if no questions, no submit button
        else return mQuestionsList.size() + 1; //+1 for the submit button
    }


    public static class TextQuestionViewHolder extends QuestionViewHolder {

        AppCompatEditText familyInfoEntry;

        public TextQuestionViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(callback, itemView);
        }

        @Override
        public void setQuestion(BackgroundQuestion question) {
            super.setQuestion(question);


            familyInfoEntry = itemView.findViewById(R.id.et_questiontext_answer);

            switch (question.getResponseType())
            {
                case INTEGER:
                    familyInfoEntry.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;

                default:
                    familyInfoEntry.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            familyInfoEntry.setText(mBackgroundQuestionCallback.getResponseFor(question));
        }

        @Override
        public void setBackgroundQuestionCallback(BackgroundQuestionCallback listener) {
            super.setBackgroundQuestionCallback(listener);

            familyInfoEntry.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String answer = familyInfoEntry.getText().toString();
                    mBackgroundQuestionCallback.onQuestionAnswered(mQuestion, answer);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    public static class DropdownViewHolder extends QuestionViewHolder {

        LinearLayout familyInfoItem;
        Spinner mSpinnerOptions;
        SurveyQuestionSpinnerAdapter mSpinnerAdapter;


        public DropdownViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(callback, itemView);

            mSpinnerOptions = (Spinner) itemView.findViewById(R.id.spinner_questiondropdown);

            mSpinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedOption = mSpinnerAdapter.getDataAt(i);
                    mSpinnerAdapter.setSelected(i);

                    mBackgroundQuestionCallback.onQuestionAnswered(mQuestion, selectedOption);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //TODO should remove last response
                }
            });
        }


        @Override
        public void setQuestion(BackgroundQuestion question) {
            super.setQuestion(question);

            if(question.getOptions() != null){

                mSpinnerAdapter =
                        new SurveyQuestionSpinnerAdapter(itemView.getContext(), R.layout.item_tv_spinner);


                mSpinnerAdapter.setValues(question.getOptions().toArray(
                        new String[question.getOptions().size()]));
                mSpinnerOptions.setAdapter(mSpinnerAdapter);

                mSpinnerAdapter.showEmptyPlaceholder();

            } else {
                throw new IllegalArgumentException("This question has no options");
            }
        }
    }


    public static class LocationViewHolder extends QuestionViewHolder{

        LinearLayout familyInfoItem;
        TextView familyInfoQuestion;
        AppCompatEditText familyInfoEntry;

        public LocationViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(callback, itemView);

            familyInfoQuestion = (TextView) itemView.findViewById(R.id.tv_questionall_title);
            familyInfoEntry = itemView.findViewById(R.id.et_questiontext_answer);
        }

        public String onResponse()
        {
            return familyInfoEntry.getText().toString();
        }
    }

    public static class PictureViewHolder extends QuestionViewHolder{

        LinearLayout familyInfoItem;
        ImageButton cameraButton;
        ImageButton galleryButton;
        ImageView responsePicture;

        public PictureViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(callback, itemView);

            familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_picturequestion);
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

        public void onResponse()
        {
            responsePicture.setVisibility(View.VISIBLE);
            cameraButton.setVisibility(View.INVISIBLE);
            galleryButton.setVisibility(View.INVISIBLE);
        }

    }

    abstract public static class QuestionViewHolder extends RecyclerView.ViewHolder
    {
        protected BackgroundQuestion mQuestion;
        BackgroundQuestionCallback mBackgroundQuestionCallback;
        TextView mTvQuestionTitle;
        ImageButton mBtnNext;

        public QuestionViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(itemView);

            mBtnNext = itemView.findViewById(R.id.btn_questionall_next);
            mTvQuestionTitle = itemView.findViewById(R.id.tv_questionall_title);

            mBackgroundQuestionCallback = callback;
            mBtnNext.setOnClickListener(mBackgroundQuestionCallback::onNext);
        }

        public void setBackgroundQuestionCallback(BackgroundQuestionCallback callback)
        {

        }

        /**Stores the question that is being set and sets the title of the question
         *
         * @param question Question to set
         */
        public void setQuestion(BackgroundQuestion question)
        {
            mQuestion = question;
            mTvQuestionTitle.setText(question.getDescription());
        }
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{

        Button mSubmitButton;
        RecyclerView mRv;
        ResponseReviewAdapter mAdapter;
        BackgroundQuestionCallback mBackgroundQuestionCallback;

        public ReviewViewHolder(BackgroundQuestionCallback callback, View itemView) {
            super(itemView);

            mBackgroundQuestionCallback = callback;

            mRv = itemView.findViewById(R.id.rv_questionsreview);
            mRv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            mAdapter = new ResponseReviewAdapter(mBackgroundQuestionCallback);

            mRv.setAdapter(mAdapter);

            mSubmitButton = (Button) itemView.findViewById(R.id.btn_surveyquestions_submit);
            mSubmitButton.setOnClickListener((view)-> mBackgroundQuestionCallback.onSubmit());
        }

        public void setQuestions(List<BackgroundQuestion> questions)
        {
            mAdapter.setQuestions(questions);
        }
    }


    public static class ResponseReviewAdapter extends RecyclerView.Adapter
    {
        BackgroundQuestionCallback mCallback;
        List<BackgroundQuestion> mQuestions;

        ResponseReviewAdapter(BackgroundQuestionCallback c)
        {
            super();
            mCallback = c;
            mQuestions = null;
        }

        void setQuestions(List<BackgroundQuestion> questions)
        {
            mQuestions = questions;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionsreview_response, parent, false);

            return new QuestionResponseViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            BackgroundQuestion q = mQuestions.get(position);
            String response = mCallback.getResponseFor(q);

            ((QuestionResponseViewHolder)holder).setFields(q, response);
        }

        @Override
        public int getItemCount() {

            if(mQuestions==null)
            {
                return 0;
            }
            else return mQuestions.size();
        }

        public static class QuestionResponseViewHolder extends RecyclerView.ViewHolder {

            TextView mTvQuestion;
            TextView mTvResponse;

            public QuestionResponseViewHolder(View itemView) {
                super(itemView);

                mTvResponse = itemView.findViewById(R.id.tv_questionresponse_response);
                mTvQuestion = itemView.findViewById(R.id.tv_questionresponse_question);
            }

            public void setFields(BackgroundQuestion q, String response)
            {
                mTvQuestion.setText(q.getDescription());
                mTvResponse.setText(response);
            }
        }

    }

    /**Fades the questions that are not centered in the Discrete Scroll View**/
    public static class QuestionFadeTransformer implements DiscreteScrollItemTransformer
    {
        @Override
        public void transformItem(View item, float position) {
            //pos inbetween -1 and 1, inclusive

            //first normalize so between 0 and 1
            //1 is max value

            if (item.getId() != R.id.submit_button_view) {
                float absPosition = Math.abs(position);
                absPosition = 1 - absPosition; //flip value.. so 1 is max

                float output = 0.7f * (absPosition) + 0.2f; //in between 100% and 20% output

                item.setAlpha(output);
            }
        }
    }

}

