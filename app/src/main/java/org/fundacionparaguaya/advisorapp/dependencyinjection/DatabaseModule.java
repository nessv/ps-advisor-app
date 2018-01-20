package org.fundacionparaguaya.advisorapp.dependencyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.local.LocalDatabase;
import org.fundacionparaguaya.advisorapp.data.local.SurveyDao;
import org.fundacionparaguaya.advisorapp.data.remote.FamilyService;
import org.fundacionparaguaya.advisorapp.data.remote.RemoteDatabase;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.repositories.SurveyRepository;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The module responsible for creating and satisfying dependencies relating to the local and remote
 * databases.
 */

@Module
public class DatabaseModule {
    private final LocalDatabase local;
    private final RemoteDatabase remote;

    public DatabaseModule(Application application) {
        this.local = Room.databaseBuilder(
                application,
                LocalDatabase.class,
                "Advisor.db"
        ).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://povertystoplightiqp.org:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.remote = new RemoteDatabase(retrofit);
    }

    @Provides
    @Singleton
    LocalDatabase provideLocalDatabase() {
        return this.local;
    }

    @Provides
    @Singleton
    RemoteDatabase provideRemoteDatabase() {
        return this.remote;
    }

    @Provides
    @Singleton
    FamilyRepository provideFamilyRepository(FamilyDao familyDao, FamilyService familyService) {
        return new FamilyRepository(familyDao, familyService);
    }

    @Provides
    @Singleton
    SurveyRepository provideSurveyRepository(SurveyDao surveyDao) {
        return new SurveyRepository(surveyDao);
    }

    @Provides
    @Singleton
    FamilyDao provideFamilyDao(LocalDatabase local) {
        return local.familyDao();
    }

    @Provides
    @Singleton
    FamilyService provideFamilyService(RemoteDatabase remote) {
        return remote.familyService();
    }

    @Provides
    @Singleton
    SurveyDao provideSurveyDao(LocalDatabase local) {
        return local.surveyDao();
    }

    @Provides
    @Singleton
    InjectionViewModelFactory provideInjectionViewModelFactory(FamilyRepository familyRepository) {
        return new InjectionViewModelFactory(familyRepository);
    }
}
