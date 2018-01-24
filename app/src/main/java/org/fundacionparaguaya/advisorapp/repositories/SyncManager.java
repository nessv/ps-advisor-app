package org.fundacionparaguaya.advisorapp.repositories;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A utility that manages the synchronization of the local databases.
 */

@Singleton
public class SyncManager {
    private FamilyRepository mFamilyRepository;
    private SurveyRepository mSurveyRepository;
    private Date lastSynced;

    @Inject
    public SyncManager(FamilyRepository familyRepository, SurveyRepository surveyRepository) {
        this.mFamilyRepository = familyRepository;
        this.mSurveyRepository = surveyRepository;
    }

    /**
     * Synchronizes the local database with the remote one.
     * @return Whether the sync was successful.
     */
    public boolean sync() {
        boolean result;
        result = mFamilyRepository.sync();
        result &= mSurveyRepository.sync();
        return result;
    }
}
