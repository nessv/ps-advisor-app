package org.fundacionparaguaya.advisorapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.runner.AndroidJUnit4;

import org.fundacionparaguaya.advisorapp.data.local.LocalDatabase;
import org.fundacionparaguaya.advisorapp.data.local.SnapshotDao;
import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static org.fundacionparaguaya.advisorapp.data.LiveDataTestUtil.waitForValue;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Green;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Red;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Yellow;

/**
 * The tests for the survey data access object.
 */

@RunWith(AndroidJUnit4.class)
public class SnapshotDaoTest {
    private LocalDatabase db;
    private SurveyDao surveyDao;
    private SnapshotDao snapshotDao;

    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(getTargetContext(), LocalDatabase.class).build();
        surveyDao = db.surveyDao();
        snapshotDao = db.snapshotDao();
    }

    @After
    public void close() {
        db.close();
    }

    @Test
    public void ShouldBeAbleToInsertASnapshot() {
        List<IndicatorQuestion> indicatorQuestions = new ArrayList<>();
        List<IndicatorOption> indicatorOptions = new ArrayList<>();
        indicatorOptions.add(new IndicatorOption("Has a stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-3.jpg", Green));
        indicatorOptions.add(new IndicatorOption("Has no stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-2.jpg", Yellow));
        indicatorOptions.add(new IndicatorOption("Has no kitchen.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-1.jpg", Red));
        Indicator indicator = new Indicator("properKitchen", "Home", indicatorOptions);
        indicatorQuestions.add(new IndicatorQuestion(indicator));

        List<EconomicQuestion> economicQuestions = new ArrayList<>();
        List<String> economicOptions = new ArrayList<>();
        economicOptions.add("Employed");
        economicOptions.add("Not Employed");
        economicQuestions.add(new EconomicQuestion("employmentStatus", "Employment status.", economicOptions));

        List<PersonalQuestion> personalQuestions = new ArrayList<>();
        personalQuestions.add(new PersonalQuestion("firstName", "First name."));

        Survey survey = new Survey(1,personalQuestions, economicQuestions, indicatorQuestions);

        surveyDao.insertSurvey(survey);

        Map<IndicatorQuestion, String> responses = new HashMap<>();

        LiveData<List<Survey>> result = surveyDao.querySurveys();
        List<Survey> value = waitForValue(result);
        assertEquals(1, value.size());
        assertEquals(survey, value.get(0));
    }
}
