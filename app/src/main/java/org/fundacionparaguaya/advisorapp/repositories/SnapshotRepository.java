package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.local.SnapshotDao;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.data.remote.SnapshotService;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.IrMapper;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SnapshotIr;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.Snapshot;
import org.fundacionparaguaya.advisorapp.models.Survey;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

import static java.lang.String.format;

/**
 * The utility for the storage of snapshots.
 */

public class SnapshotRepository {
    private static final String TAG = "SnapshotRepository";

    private final SnapshotDao snapshotDao;
    private final SnapshotService snapshotService;
    private final AuthenticationManager authManager;

    private final FamilyRepository familyRepository;
    private final SurveyRepository surveyRepository;

    @Inject
    public SnapshotRepository(SnapshotDao snapshotDao,
                              SnapshotService snapshotService,
                              AuthenticationManager authManager,
                              FamilyRepository familyRepository,
                              SurveyRepository surveyRepository) {
        this.snapshotDao = snapshotDao;
        this.snapshotService = snapshotService;
        this.authManager = authManager;
        this.familyRepository = familyRepository;
        this.surveyRepository = surveyRepository;
    }

    public LiveData<List<Snapshot>> getSnapshots(Family family) {
        return snapshotDao.querySnapshotsForFamily(family.getId());
    }

    public void saveSnapshot(Snapshot snapshot) {
        snapshotDao.insertSnapshot(snapshot);
    }

    private boolean pushSnapshots() {
        List<Snapshot> pending = snapshotDao.queryPendingSnapshots();
        boolean success = true;

        // attempt to push each of the pending snapshots
        for (Snapshot snapshot : pending) {
            try {
                Family family = familyRepository.getFamilyNow(snapshot.getFamilyId());
                Survey survey = surveyRepository.getSurveyNow(snapshot.getSurveyId());
                Response<SnapshotIr> response = snapshotService
                        .postSnapshot(authManager.getAuthenticationString(), IrMapper.mapSnapshot(snapshot, survey))
                        .execute();

                if (response.isSuccessful() && response.body() != null) {
                    // overwrite the pending snapshot with the snapshot from remote db
                    Snapshot remoteSnapshot = IrMapper.mapSnapshot(response.body(), family, survey);
                    remoteSnapshot.setId(snapshot.getId());
                    snapshotDao.updateSnapshot(remoteSnapshot);
                } else {
                    success = false;
                }
            } catch (IOException e) {
                Log.e(TAG, String.format("pushFamilies: Could not push snapshot \"%d\"!", snapshot.getId()), e);
                success = false;
            }
        }
        return success;
    }

    private boolean pullSnapshots() {
        boolean success = true;
        for (Family family : familyRepository.getFamiliesNow()) {
            for (Survey survey : surveyRepository.getSurveysNow()) {
                try {
                    Response<List<SnapshotIr>> response = snapshotService
                            .getSnapshots(authManager.getAuthenticationString(), survey.getRemoteId(), family.getRemoteId())
                            .execute();

                    if (response.isSuccessful() && response.body() != null) {
                        List<Snapshot> snapshots = IrMapper.mapSnapshots(response.body(), family, survey);
                        snapshotDao.insertSnapshots(snapshots.toArray(new Snapshot[snapshots.size()]));
                    }
                } catch (IOException e) {
                    Log.e(TAG, format("pullSnapshots: Could not pull snapshots for family \"%s\"!", family.getName()), e);
                    success = false;
                }
            }
        }
        return success;
    }

    /**
     * Synchronizes the local snapshots with the remote database.
     * @return Whether the sync was successful.
     */
    boolean sync() {
        boolean successful;
        successful = pushSnapshots();
        if (successful) {
            successful = pullSnapshots();
        }

        return successful;
    }
}
