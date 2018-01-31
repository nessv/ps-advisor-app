package org.fundacionparaguaya.advisorapp.adapters;

import android.content.Intent;
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

import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.callbacks.QuestionResponseListener;
import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;

import java.util.List;

public class BackgroundQuestionAdapter extends RecyclerView.Adapter {

    int STRING_INPUT = 1;
    int LOCATION_INPUT = 2;
    int PHOTO_INPUT = 3;
    int SUBMIT_BUTTON = 4;

    List<BackgroundQuestion> mQuestionsList;
    QuestionResponseListener mQuestionResponseListener;

    public BackgroundQuestionAdapter(QuestionResponseListener listener){
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

        if(position >= getItemCount()){

            return SUBMIT_BUTTON;

        } else {
            switch (question.getResponseType()) {
                case STRING:
                case PHONE_NUMBER:
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
        else if (viewType == SUBMIT_BUTTON){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfamily_submit, parent, false);
            return new SubmitViewHolder(view);
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
        Button nextButton;

        public TextQuestionViewHolder(View itemView) {
            super(itemView);
            familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_textquestion);
            familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_textquestion);
            familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_text_field);
            nextButton = (Button) itemView.findViewById(R.id.next_button);


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

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
        Button nextButton;

        public LocationViewHolder(View itemView) {
            super(itemView);

            familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_textquestion);
            familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_locationquestion);
            familyInfoEntry = (EditText) itemView.findViewById(R.id.entry_location_field);
            nextButton = (Button) itemView.findViewById(R.id.next_button);

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
        Button nextButton;


        public PictureViewHolder(View itemView) {
            super(itemView);
            familyInfoItem = (LinearLayout) itemView.findViewById(R.id.item_picturequestion);
            familyInfoQuestion = (TextView) itemView.findViewById(R.id.addfamily_picturequestion);
            cameraButton = (ImageButton) itemView.findViewById(R.id.camera_button);
            galleryButton = (ImageButton) itemView.findViewById(R.id.gallery_button);
            nextButton = (Button) itemView.findViewById(R.id.next_button);

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

    public class SubmitViewHolder extends RecyclerView.ViewHolder{

        LinearLayout submitButtonContainer;
        Button submitButton;

        public SubmitViewHolder(View itemView) {
            super(itemView);

            submitButtonContainer = (LinearLayout) itemView.findViewById(R.id.submit_button_view);
            submitButton = (Button) itemView.findViewById(R.id.submit_button);

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

            float absPosition = Math.abs(position);
            absPosition = 1-absPosition; //flip value.. so 1 is max

            float output = 0.7f * (absPosition) + 0.2f; //in between 100% and 20% output

            item.setAlpha(output);
        }
    }

}

