package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.fundacionparaguaya.advisorapp.models.*;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Survey view model that is shared across all of the fragments/activity related to the view model
 */

public class SharedSurveyViewModel extends ViewModel
{
    public enum SurveyState {NONE, INTRO, BACKGROUND_QUESTIONS, INDICATORS, REVIEW}

    static String NO_SNAPSHOT_EXCEPTION_MESSAGE = "Method call requires an existing snapshot, but no snapshot has been created. (Call" +
            "makeSnapshot before this function";

    SurveyRepository mSurveyRepository;
    FamilyRepository mFamilyRepository;

    MutableLiveData<SurveyProgress> mProgress = new MutableLiveData<SurveyProgress>();
    private MutableLiveData<SurveyState> mSurveyState;

    List<IndicatorQuestion> mSkippedIndicators;

    MutableLiveData<Snapshot> mSnapshot;

    LiveData<Family> mFamily;

    Survey mSurvey;

    private int mSurveyId;
    private int mFamilyId;

    public SharedSurveyViewModel(SurveyRepository surveyRepository, FamilyRepository familyRepository) {
        super();

        mSurveyRepository = surveyRepository;
        mFamilyRepository = familyRepository;

        mProgress.setValue(new SurveyProgress());
        mSurveyState = new MutableLiveData<>();

        mSurveyState.setValue(SurveyState.NONE);
        mSnapshot = new MutableLiveData<Snapshot>();

        mSkippedIndicators = new ArrayList<>();
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

    public MutableLiveData<SurveyProgress> getProgress()
    {
        return mProgress;
    }

    public void addSkippedIndicator(IndicatorQuestion question) {
        mSkippedIndicators.add(question);
    }

    public List<IndicatorQuestion> getSkippedIndicators()
    {
        return mSkippedIndicators;
    }

    public @Nullable IndicatorOption getResponseForIndicator(IndicatorQuestion question)
    {
        return getSnapshotValue().getIndicatorResponses().get(question);
    }

    public void addIndicatorResponse(IndicatorQuestion indicator, IndicatorOption response)
    {
        getSnapshotValue().response(indicator, response);
    }

    public void addBackgroundResponse(SurveyQuestion question, String response)
    {
        if(question instanceof PersonalQuestion)
        {
            getSnapshotValue().response((PersonalQuestion) question, response);
        }
        else if(question instanceof EconomicQuestion)
        {
            getSnapshotValue().response((EconomicQuestion) question, response);
        }
    }

    public @Nullable String getBackgroundResponse(SurveyQuestion question)
    {
        if(question instanceof PersonalQuestion)
        {
            return getSnapshotValue().getPersonalResponses().get(question);
        }
        else if(question instanceof EconomicQuestion)
        {
            return getSnapshotValue().getEconomicResponses().get(question);
        }

        return null;
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

        void setDescription(String description)
        {
            mProgressDescription = description;
        }

        void setPercentageComplete(int percentage)
        {
            mPercentageComplete = percentage;
        }

        String getDescription()
        {
            return mProgressDescription;
        }

        int getPercentageComplete()
        {
            return mPercentageComplete;
        }
    }
}
