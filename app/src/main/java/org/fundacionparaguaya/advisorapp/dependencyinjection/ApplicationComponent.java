package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;

import org.fundacionparaguaya.advisorapp.activities.DashActivity;
import org.fundacionparaguaya.advisorapp.activities.SurveyActivity;
import org.fundacionparaguaya.advisorapp.fragments.*;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The main application component.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    Application application();

    void inject(LoginFragment loginFragment);

    void inject(DashActivity dashActivity);
    void inject(AllFamiliesStackedFrag allFamiliesFragment);
    void inject(FamilyDetailFrag familyDetailFrag);

    void inject(SurveyActivity surveyActivity);
    void inject(SurveyIntroFragment surveyIntroFragment);

    void inject(SurveyQuestionsFrag backgroundQuestionsFrag);
    void inject(SurveyIndicatorsFragment surveyIndicatorsFragment);

    void inject(SurveySummaryFragment surveySummaryFragment);
}
