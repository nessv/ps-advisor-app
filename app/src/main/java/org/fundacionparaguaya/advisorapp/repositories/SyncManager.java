package org.fundacionparaguaya.advisorapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A utility that manages the synchronization of the local databases.
 */

@Singleton
public class SyncManager {
    private static final String TAG = "SyncManager";
    private static final String PREFS_SYNC = "sync";
    private static final String KEY_LAST_SYNC_TIME = "lastSyncTime";
    
    private FamilyRepository mFamilyRepository;
    private SurveyRepository mSurveyRepository;
    private SharedPreferences mPreferences;

    private MutableLiveData<Long> mLastSyncedTime;

    @Inject
    SyncManager(Application application, FamilyRepository familyRepository, SurveyRepository surveyRepository) {
        this.mFamilyRepository = familyRepository;
        this.mSurveyRepository = surveyRepository;

        mPreferences = application.getApplicationContext()
                .getSharedPreferences(PREFS_SYNC, MODE_PRIVATE);

        mLastSyncedTime = new MutableLiveData<>();
        mLastSyncedTime.setValue(mPreferences.getLong(KEY_LAST_SYNC_TIME, -1));
    }

    /**
     * Synchronizes the local database with the remote one.
     * @return Whether the sync was successful.
     */
    public boolean sync() {
        Log.d(TAG, "sync: Synchronizing the database...");
        boolean result;
        result = mFamilyRepository.sync();
        result &= mSurveyRepository.sync();
        Log.d(TAG, String.format("sync: Finished the synchronization %s.",
                result ? "successfully" : "with errors"));

        if (result) {
            updateLastSyncedTime();
        }
        return result;
    }

    private void updateLastSyncedTime() {
        //post because this is run on a background thread
        mLastSyncedTime.postValue(new Date().getTime());

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(KEY_LAST_SYNC_TIME, mLastSyncedTime.getValue());
        editor.apply();
    }

    public LiveData<Long> getLastSyncedTime() {
        return mLastSyncedTime;
    }
}
