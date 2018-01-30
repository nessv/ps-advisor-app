package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.models.*;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.repositories.SurveyRepository;

import java.util.*;

/**
 * Survey view model that is shared across all of the fragments/activity related to the view model
 */

public class SharedSurveyViewModel extends ViewModel
{
    public enum SurveyState {NONE, INTRO, BACKGROUND_QUESTIONS, INDICATORS, SUMMARY, REVIEWINDICATORS, REVIEWBACKGROUND}

    static String NO_SNAPSHOT_EXCEPTION_MESSAGE = "Method call requires an existing snapshot, but no snapshot has been created. (Call" +
            "makeSnapshot before this function";

    SurveyRepository mSurveyRepository;
    FamilyRepository mFamilyRepository;

    MutableLiveData<SurveyProgress> mProgress = new MutableLiveData<SurveyProgress>();
    private MutableLiveData<SurveyState> mSurveyState;

    Set<IndicatorQuestion> mSkippedIndicators;

    MutableLiveData<Snapshot> mSnapshot;

    LiveData<Family> mFamily;

    Survey mSurvey;

    IndicatorQuestion focusedQuestion;

    private int mSurveyId;
    private int mFamilyId;

    public SharedSurveyViewModel(SurveyRepository surveyRepository, FamilyRepository familyRepository) {
        super();

        mSurveyRepository = surveyRepository;
        mFamilyRepository = familyRepository;

        mSurveyState = new MutableLiveData<>();

        mSurveyState.setValue(SurveyState.NONE);
        mSnapshot = new MutableLiveData<Snapshot>();

        mSkippedIndicators = new HashSet<>();
    }

    public LiveData<Family> getCurrentFamily()
    {
        return mFamily;
    }

    /**
     * Sets the family that is taking the survey
     *
     * @param familyId Id of the family taking the survey
     */
    public void setFamily(int familyId)
    {
        mFamilyId = familyId;
        mFamily = mFamilyRepository.getFamily(familyId);
    }
    /**
     * Makes a new snapshot based on the family set and the survey provided.
     *
     * Assumes that family live data object .getValue is not null
     *
     * We should wait for this before proceeding from the start screen to the next screen
     *
     */
    public void makeSnapshot(Survey survey)
    {
        mSurvey = survey;

        mSnapshot.setValue(new Snapshot(mFamily.getValue(), mSurvey));
    }

    public LiveData<Snapshot> getSnapshot()
    {
        return mSnapshot;
    }

    public Survey getSurveyInProgress()
    {
        return mSurvey;
    }

    /**
     * Returns the surveys available to take.
     */
    public LiveData<List<Survey>> getSurveys()
    {
        return mSurveyRepository.getSurveys();
    }

    public MutableLiveData<SurveyState> getSurveyState()
    {
        if(mSurveyState==null)
        {
            mSurveyState = new MutableLiveData<SurveyState>();
        }

        return mSurveyState;
    }

    public void setSurveyState(SurveyState state)
    {
        mSurveyState.setValue(state);

        calculateProgress();
    }

    public void setFocusedQuestion(String name){
        for (IndicatorQuestion question:mSkippedIndicators){
            if (question.getName().equals(name)){
                setFocusedQuestion(question);
                break;
            }
        }
    }

    public void setFocusedQuestion(IndicatorQuestion question){
        focusedQuestion = question;
    }

    public IndicatorQuestion getFocusedQuestion(){
        return focusedQuestion;
    }

    public MutableLiveData<SurveyProgress> getProgress()
    {
        return mProgress;
    }

    public void addSkippedIndicator(IndicatorQuestion question) {
        //clears any responses for the question
        mSnapshot.getValue().getIndicatorResponses().remove(question);

        //skipped indicators is a hashset, so there will be no duplicate entries.
        mSkippedIndicators.add(question);
        calculateProgress();
    }

    public Set<IndicatorQuestion> getSkippedIndicators()
    {
        return mSkippedIndicators;
    }

    public @Nullable IndicatorOption getResponseForIndicator(IndicatorQuestion question)
    {
        return getSnapshotValue().getIndicatorResponses().get(question);

    }

    public void addIndicatorResponse(IndicatorQuestion indicator, IndicatorOption response)
    {
        if(response!=null) {
            if(mSkippedIndicators.contains(indicator))
            {
                mSkippedIndicators.remove(indicator);
            }

            getSnapshotValue().response(indicator, response);

            calculateProgress();
        }
    }

    public void removeIndicatorResponse(IndicatorQuestion question){
        mSnapshot.getValue().getIndicatorResponses().remove(question);
        calculateProgress();
    }

    public void addBackgroundResponse(BackgroundQuestion question, String response)
    {
        //TODO if string is empty, we probably want to remove any response that we used to have...?
        if(response!=null && !response.isEmpty()) {
            getSnapshotValue().response(question, response);
            calculateProgress();
        }
    }

    public @Nullable String getBackgroundResponse(BackgroundQuestion question)
    {
        return getSnapshotValue().getBackgroundResponse(question);
    }

    public void calculateProgress()
    {
        if(getSurveyInProgress() != null && mSurveyState.getValue() != null)
        {
            int progress = 0;
            String progressString = "";

            switch (mSurveyState.getValue())
            {

                case BACKGROUND_QUESTIONS:

                    int totalQuestions = getSurveyInProgress().getEconomicQuestions().size() +
                            getSurveyInProgress().getPersonalQuestions().size();

                    int completedQuestions = mSnapshot.getValue().getPersonalResponses().size() +
                            mSnapshot.getValue().getEconomicResponses().size();

                    progress = (100*completedQuestions)/totalQuestions;
                    progressString = (totalQuestions-completedQuestions) + " Questions Remaining";

                    break;

                case INDICATORS:
                    int totalIndicators = getSurveyInProgress().getIndicatorQuestions().size();

                    int skippedIndicators = mSkippedIndicators.size();
                    int completedIndicators = mSnapshot.getValue().getIndicatorResponses().size();

                    progress = (100* (completedIndicators + skippedIndicators))/totalIndicators;
                    progressString = (totalIndicators-(completedIndicators+skippedIndicators)) + " Indicators Remaining, " +
                    skippedIndicators + " Skipped" ;

            }

            mProgress.setValue(new SurveyProgress(progress, progressString));
        }
    }

    public static class IndicatorSurvey{

    }
    /**
     * Essentially "unwraps" the Snapshot live data and retrieves the value. If the value is null, it throws
     * an illegal state exception
     *
     * @return Snapshot in process
     * @throws IllegalStateException
     */
        private @NonNull Snapshot getSnapshotValue()
        {
            Snapshot value = mSnapshot.getValue();

            if(value == null)
            {
                throw new IllegalStateException(NO_SNAPSHOT_EXCEPTION_MESSAGE);
            }
            else return value;
    }

    public static class SurveyProgress
    {
        String mProgressDescription;
        int mPercentageComplete;

        SurveyProgress(int percentage, String description)
        {
            mPercentageComplete = percentage;
            mProgressDescription =description;
        }

        void setDescription(String description)
        {
            mProgressDescription = description;
        }

        void setPercentageComplete(int percentage)
        {
            mPercentageComplete = percentage;
        }

        public String getDescription()
        {
            return mProgressDescription;
        }

        public int getPercentageComplete()
        {
            return mPercentageComplete;
        }
    }
}
