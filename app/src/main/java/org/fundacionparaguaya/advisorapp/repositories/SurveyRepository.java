package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;

import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.fundacionparaguaya.advisorapp.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Green;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Red;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Yellow;

/**
 * The utility for the storage of surveys and snapshots.
 */

public class SurveyRepository {
    private final SurveyDao surveyDao;

    private User user;

    @Inject
    public SurveyRepository(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;

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
        economicOptions.add("Employed");
        economicOptions.add("Not Employed");
        economicQuestions.add(new EconomicQuestion("employmentStatus", "Employment status.", economicOptions));

        List<PersonalQuestion> personalQuestions = new ArrayList<>();
        personalQuestions.add(new PersonalQuestion("firstName", "First name."));

        surveyDao.insertSurvey(new Survey(1,personalQuestions, economicQuestions, indicatorQuestions));
    }

    //region Survey
    public LiveData<List<Survey>> getSurveys() {
        return surveyDao.querySurveys();
    }

    public LiveData<Survey> getSurvey(int id) {
        return surveyDao.querySurvey(id);
    }
    //endregion

    //region Snapshot

    //endregion
}
