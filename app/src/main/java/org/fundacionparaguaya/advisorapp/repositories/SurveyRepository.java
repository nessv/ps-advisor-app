package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.data.remote.*;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;
import org.fundacionparaguaya.advisorapp.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.Economic;
import static org.fundacionparaguaya.advisorapp.models.BackgroundQuestion.QuestionType.Personal;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.*;

/**
 * The utility for the storage of surveys and snapshots.
 */

public class SurveyRepository {
    private static final String TAG = "SurveyRepository";

    private final SurveyDao surveyDao;
    private final SurveyService surveyService;
    private final AuthenticationManager authManager;

    @Inject
    public SurveyRepository(SurveyDao surveyDao,
                            SurveyService surveyService,
                            AuthenticationManager authManager) {
        this.surveyDao = surveyDao;
        this.surveyService = surveyService;
        this.authManager = authManager;

        //because this in the constructor, can't be done in the main thread.
        AsyncTask.execute(() ->
        {
            List<IndicatorQuestion> indicatorQuestions = new ArrayList<>();
            List<IndicatorOption> indicatorOptions = new ArrayList<>();
            indicatorOptions.add(new IndicatorOption("Has a stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-3.jpg", Green));
            indicatorOptions.add(new IndicatorOption("Has no stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-2.jpg", Yellow));
            indicatorOptions.add(new IndicatorOption("Has no kitchen.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-1.jpg", Red));
            indicatorQuestions.add(new IndicatorQuestion(new Indicator("properKitchen", "Home", indicatorOptions)));

            indicatorOptions = new ArrayList<>();
            indicatorOptions.add(new IndicatorOption("Has a phone.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-3.jpg", Green));
            indicatorOptions.add(new IndicatorOption("Has a dead phone.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-2.jpg", Yellow));
            indicatorOptions.add(new IndicatorOption("Has no phone.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-1.jpg", Red));
            indicatorQuestions.add(new IndicatorQuestion(new Indicator("phone", "Home", indicatorOptions)));

            indicatorOptions = new ArrayList<>();
            indicatorOptions.add(new IndicatorOption("Another question, green.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-3.jpg", Green));
            indicatorOptions.add(new IndicatorOption("Another question, yellow", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-2.jpg", Yellow));
            indicatorOptions.add(new IndicatorOption("Another question, red", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-1.jpg", Red));
            indicatorQuestions.add(new IndicatorQuestion(new Indicator("Another Question", "Home", indicatorOptions)));

            indicatorOptions = new ArrayList<>();
            indicatorOptions.add(new IndicatorOption("Yet another question, green.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-3.jpg", Green));
            indicatorOptions.add(new IndicatorOption("Yet another question, yellow", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-2.jpg", Yellow));
            indicatorOptions.add(new IndicatorOption("Yet another question, red", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-1.jpg", Red));
            indicatorQuestions.add(new IndicatorQuestion(new Indicator("Yet Another Question", "Home", indicatorOptions)));

            indicatorOptions = new ArrayList<>();
            indicatorOptions.add(new IndicatorOption("A longer question that goes into a little detail, green", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-3.jpg", Green));
            indicatorOptions.add(new IndicatorOption("An even longer question that really gets into the nitty " +
                    "gritty of the family's situation, yellow", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-2.jpg", Yellow));
            indicatorOptions.add(new IndicatorOption("Yet another question that is even longer. extremely long, and infact the longest of the long." +
                    ", red", "https://s3.us-east-2.amazonaws.com/fp-psp-images/25-1.jpg", Red));
            indicatorQuestions.add(new IndicatorQuestion(new Indicator("Yet Another Long Question", "Home", indicatorOptions)));


            List<BackgroundQuestion> economicQuestions = new ArrayList<>();
            List<String> economicOptions = new ArrayList<>();
            economicOptions.add("Employed");
            economicOptions.add("Not Employed");
            economicQuestions.add(new BackgroundQuestion("employmentStatus", "Employment status.", ResponseType.String, Economic, economicOptions));

            List<BackgroundQuestion> personalQuestions = new ArrayList<>();

            personalQuestions.add(new BackgroundQuestion("name", "What is your name?", ResponseType.String, Personal));
            personalQuestions.add(new BackgroundQuestion("income", "What is your annual income?", ResponseType.Integer, Personal));

            surveyDao.insertSurvey(new Survey(1,personalQuestions, economicQuestions, indicatorQuestions));
        });
    }

    //region Survey
    public LiveData<List<Survey>> getSurveys() {
        return surveyDao.querySurveys();
    }

    public LiveData<Survey> getSurvey(int id) {
        return surveyDao.querySurvey(id);
    }

    /**
     * Synchronizes the local surveys with the remote database.
     * @return Whether the sync was successful.
     */
    boolean sync() {
        try {
            Response<List<SurveyIr>> response =
                    surveyService.getSurveys(authManager.getAuthenticationString()).execute();

            if (!response.isSuccessful()) {
                return false;
            }

            if (response.body() == null) {
                return false;
            }

            List<Survey> surveys = new ArrayList<>(response.body().size());
            for (SurveyIr surveyIr : response.body()) {
                surveys.add(surveyIr.compile());
            }
            surveyDao.insertSurveys(surveys.toArray(new Survey[surveys.size()]));
        } catch (IOException e) {
            Log.e(TAG, "sync: Could not sync the survey repository!", e);
            return false;
        }
        return true;
    }
    //endregion
}
