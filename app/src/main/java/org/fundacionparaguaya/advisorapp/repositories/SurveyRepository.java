package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.data.remote.SurveyService;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.ResponseType;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Green;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Red;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Yellow;

/**
 * The utility for the storage of surveys and snapshots.
 */

public class SurveyRepository {
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

            List<EconomicQuestion> economicQuestions = new ArrayList<>();
            List<String> economicOptions = new ArrayList<>();
            economicOptions.add("");
            economicOptions.add("Not Employed");
            economicQuestions.add(new EconomicQuestion("employmentStatus", "Employment status.", ResponseType.String, economicOptions));

            List<PersonalQuestion> personalQuestions = new ArrayList<>();
            personalQuestions.add(new PersonalQuestion("income", "Income.", ResponseType.Integer));

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
            Log.e("Synchronization", "sync: Could not sync the survey repository!", e);
            return false;
        }
        return true;
    }
    //endregion
}
