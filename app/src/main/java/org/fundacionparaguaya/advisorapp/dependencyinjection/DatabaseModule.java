package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import org.fundacionparaguaya.advisorapp.data.AdvisorDatabase;
import org.fundacionparaguaya.advisorapp.data.AdvisorRepository;
import org.fundacionparaguaya.advisorapp.data.FamilyDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * The module responsible for creating and satisfying dependencies relating to the database.
 */

@Module
public class DatabaseModule {
    private final AdvisorDatabase database;

    public DatabaseModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                AdvisorDatabase.class,
                "Advisor.db"
        ).build();
    }

    @Provides
    @Singleton
    AdvisorRepository provideAdvisorRepository(FamilyDao familyDao) {
        return new AdvisorRepository(familyDao);
    }

    @Provides
    @Singleton
    FamilyDao provideFamilyDao(AdvisorDatabase database) {
        return database.familyDao();
    }
}
