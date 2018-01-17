package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;

import org.fundacionparaguaya.advisorapp.fragments.AllFamiliesFragment;
import org.fundacionparaguaya.advisorapp.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The main application component.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    Application application();

    void inject(AllFamiliesFragment allFamiliesFragment);
    void inject(LoginFragment loginFragment);
}
