package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;

import org.fundacionparaguaya.advisorapp.activities.SurveyActivity;
import org.fundacionparaguaya.advisorapp.fragments.AllFamiliesStackedFrag;
import org.fundacionparaguaya.advisorapp.fragments.FamilyDetailFrag;
import org.fundacionparaguaya.advisorapp.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;
import org.fundacionparaguaya.advisorapp.fragments.SurveyIntroFragment;

/**
 * The main application component.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    Application application();

    void inject(AllFamiliesStackedFrag allFamiliesFragment);

    void inject(LoginFragment loginFragment);
<<<<<<< HEAD
    void inject(FamilyDetailFrag familyDetailFrag);
=======

    void inject(SurveyActivity surveyActivity);

    void inject(SurveyIntroFragment surveyIntroFragment);

>>>>>>> fd28913e5dc15c0c8bee514dc7fd3be361a9d5e2
}
