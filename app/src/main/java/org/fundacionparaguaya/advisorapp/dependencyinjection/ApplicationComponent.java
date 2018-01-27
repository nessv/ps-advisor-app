package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;

import org.fundacionparaguaya.advisorapp.activities.DashActivity;
import org.fundacionparaguaya.advisorapp.activities.SurveyActivity;
import org.fundacionparaguaya.advisorapp.fragments.AllFamiliesStackedFrag;
import org.fundacionparaguaya.advisorapp.fragments.BackgroundQuestionsFrag;
import org.fundacionparaguaya.advisorapp.fragments.FamilyDetailFrag;
import org.fundacionparaguaya.advisorapp.fragments.LoginFragment;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;

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

    void inject(BackgroundQuestionsFrag backgroundQuestionsFrag);
}
