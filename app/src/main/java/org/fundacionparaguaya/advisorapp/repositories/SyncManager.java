package org.fundacionparaguaya.advisorapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.evernote.android.job.JobRequest;

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
    private SnapshotRepository mSnapshotRepository;
    private SharedPreferences mPreferences;

    private MutableLiveData<SyncState> mState;

    @Inject
    SyncManager(Application application,
                FamilyRepository familyRepository,
                SurveyRepository surveyRepository,
                SnapshotRepository snapshotRepository) {
        this.mFamilyRepository = familyRepository;
        this.mSurveyRepository = surveyRepository;
        this.mSnapshotRepository = snapshotRepository;

        mPreferences = application.getApplicationContext()
                .getSharedPreferences(PREFS_SYNC, MODE_PRIVATE);

        mState = new MutableLiveData<>();
        mState.setValue(new SyncState(false, mPreferences.getLong(KEY_LAST_SYNC_TIME, -1), null));
    }

    public LiveData<SyncState> getState() {
        return mState;
    }

    /**
     * Synchronizes the local database with the remote one.
     * @return Whether the sync was successful.
     */
    public boolean sync() {
        Log.d(TAG, "sync: Synchronizing the database...");
        updateIsSyncing(true);
        boolean result;
        result = mFamilyRepository.sync();
        result &= mSurveyRepository.sync();
        result &= mSnapshotRepository.sync();

        Log.d(TAG, String.format("sync: Finished the synchronization %s.",
                result ? "successfully" : "with errors"));

        updateIsSyncing(false);
        if (result) {
            updateLastSyncedTime();
        }
        return result;
    }

    private void updateLastSyncedTime() {
        SyncState state = mState.getValue();
        long lastSync = new Date().getTime();
        mState.postValue(new SyncState(state.isSyncing(), lastSync, state.getErrorMessage()));

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(KEY_LAST_SYNC_TIME, lastSync);
        editor.apply();
    }

    private void updateIsSyncing(boolean isSyncing) {
        SyncState state = mState.getValue();
        mState.postValue(new SyncState(isSyncing, state.getLastSyncedTime(), state.getErrorMessage()));
    }

    public static void start() {
        new JobRequest.Builder(TAG)
                .setPeriodic(900000)
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    public class SyncState {
        private boolean mIsSyncing;
        private long mLastSyncedTime;
        private String mErrorMessage;

        SyncState(boolean isSyncing, long lastSyncedTime, String errorMessage) {
            this.mIsSyncing = isSyncing;
            this.mLastSyncedTime = lastSyncedTime;
            this.mErrorMessage = errorMessage;
        }

        public long getLastSyncedTime() {
            return mLastSyncedTime;
        }

        public String getErrorMessage() {
            return mErrorMessage;
        }

        public boolean isSyncing() {
            return mIsSyncing;
        }
    }
}
