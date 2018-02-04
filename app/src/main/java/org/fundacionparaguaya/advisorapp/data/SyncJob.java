package org.fundacionparaguaya.advisorapp.data;

import android.support.annotation.NonNull;
import android.text.format.Time;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import org.fundacionparaguaya.advisorapp.repositories.SyncManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * A schedulable job for syncing the application.
 */

public class SyncJob extends Job {
    public static final String TAG = "SyncJob";

    private SyncManager mSyncManager;

    public SyncJob(SyncManager syncManager) {
        super();
        this.mSyncManager = syncManager;
    }

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        if (mSyncManager.sync())
            return Result.SUCCESS;
        else
            return Result.FAILURE;
    }

    public static void schedule() {
        new JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15L))
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
