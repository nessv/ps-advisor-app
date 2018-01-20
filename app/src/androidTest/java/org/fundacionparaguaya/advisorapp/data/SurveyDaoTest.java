package org.fundacionparaguaya.advisorapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.runner.AndroidJUnit4;

import org.fundacionparaguaya.advisorapp.data.local.LocalDatabase;
import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.models.Indicator;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.Survey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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
public class SurveyDaoTest {
    private LocalDatabase db;
    private SurveyDao surveyDao;


    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(getTargetContext(), LocalDatabase.class).build();
        surveyDao = db.surveyDao();
    }

    @After
    public void close() {
        db.close();
    }

    @Test
    public void ShouldBeAbleToInsertASurvey() {
        List<IndicatorQuestion> questions = new ArrayList<>();
        List<IndicatorOption> options = new ArrayList<>();
        options.add(new IndicatorOption("Has a stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-3.jpg", Green));
        options.add(new IndicatorOption("Has no stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-2.jpg", Yellow));
        options.add(new IndicatorOption("Has no kitchen.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-1.jpg", Red));
        Indicator indicator = new Indicator("properKitchen", "Home", options);
        Survey survey = new Survey(1, questions);

        surveyDao.insertSurvey(survey);

        LiveData<List<Survey>> result = surveyDao.querySurveys();
        List<Survey> value = waitForValue(result);
        assertEquals(1, value.size());
        assertEquals(survey, value.get(0));
    }
}
