package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.data.remote.SurveyService;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.IrMapper;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

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

            List<Survey> surveys = IrMapper.mapSurveys(response.body());
            surveyDao.insertSurveys(surveys.toArray(new Survey[surveys.size()]));
        } catch (IOException e) {
            Log.e(TAG, "sync: Could not sync the survey repository!", e);
            return false;
        }
        return true;
    }
    //endregion
}
