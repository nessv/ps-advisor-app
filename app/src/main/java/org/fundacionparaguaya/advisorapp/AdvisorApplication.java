package org.fundacionparaguaya.advisorapp;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

import org.fundacionparaguaya.advisorapp.dependencyinjection.ApplicationComponent;
import org.fundacionparaguaya.advisorapp.dependencyinjection.ApplicationModule;
import org.fundacionparaguaya.advisorapp.dependencyinjection.DaggerApplicationComponent;
import org.fundacionparaguaya.advisorapp.dependencyinjection.DatabaseModule;
import org.fundacionparaguaya.advisorapp.jobs.JobCreator;

/**
 * The advisor application.
 */

public class AdvisorApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();

        new Instabug.Builder(this, "c66647f3c86b136c76d64c27f2a4bb12")
                .setInvocationEvent(InstabugInvocationEvent.FLOATING_BUTTON)
                .build();

        JobManager.create(this).addJobCreator(new JobCreator(this));

        Fresco.initialize(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
