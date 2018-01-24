package org.fundacionparaguaya.advisorapp.data.remote;

import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A task for synchronizing surveys from the remote database to the local one. This will
 * grab surveys from the remote database and insert them into the local one, asynchronously.
 */

public class SurveySynchronizeTask extends AsyncTask<Void, Void, Boolean> {
    private SurveyDao surveyDao;
    private SurveyService surveyService;
    private AuthenticationManager authManager;

    public SurveySynchronizeTask(SurveyDao surveyDao,
                                 SurveyService surveyService,
                                 AuthenticationManager authManager) {
        this.surveyDao = surveyDao;
        this.surveyService = surveyService;
        this.authManager = authManager;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
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
            return false;
        }
        return true;
    }
}
