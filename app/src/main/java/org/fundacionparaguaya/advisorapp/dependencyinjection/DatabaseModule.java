package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import org.fundacionparaguaya.advisorapp.data.LocalDatabase;
import org.fundacionparaguaya.advisorapp.data.FamilyMemberDao;
import org.fundacionparaguaya.advisorapp.rapositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.data.FamilyDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * The module responsible for creating and satisfying dependencies relating to the database.
 */

@Module
public class DatabaseModule {
    private final LocalDatabase database;

    public DatabaseModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                LocalDatabase.class,
                "Advisor.db"
        ).build();
    }

    @Provides
    @Singleton
    FamilyRepository provideAdvisorRepository(
            FamilyDao familyDao, FamilyMemberDao familyMemberDao) {
        return new FamilyRepository(familyDao, familyMemberDao);
    }

    @Provides
    @Singleton
    FamilyDao provideFamilyDao(LocalDatabase database) {
        return database.familyDao();
    }

    @Provides
    @Singleton
    FamilyMemberDao provideFamilyMemberDao(LocalDatabase database) {
        return database.familyMemberDao();
    }
}
