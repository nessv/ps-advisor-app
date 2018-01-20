package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.models.User;

import java.util.List;

import javax.inject.Inject;

/**
 * The utility for the storage of surveys and snapshots.
 */

public class SurveyRepository {
    private final SurveyDao surveyDao;

    private User user;

    @Inject
    public SurveyRepository(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    //region Survey
    public LiveData<List<Survey>> getSurveys() {
        return surveyDao.querySurveys();
    }

    public LiveData<Survey> getSurvey(int id) {
        return surveyDao.querySurvey(id);
    }
    //endregion
}
