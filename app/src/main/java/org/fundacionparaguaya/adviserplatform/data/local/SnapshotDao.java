package org.fundacionparaguaya.adviserplatform.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.fundacionparaguaya.adviserplatform.data.model.Snapshot;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;

/**
 * The access utility for retrieving snapshots from the local database.
 */

@Dao
public interface SnapshotDao {
    @Query("SELECT * FROM snapshots WHERE family_id = :familyId AND in_progress = 0")
    LiveData<List<Snapshot>> queryFinishedSnapshotsForFamily(int familyId);

    @Query("SELECT * FROM snapshots WHERE family_id = :familyId AND in_progress = 1")
    LiveData<Snapshot> queryInProgressSnapshotForFamily(int familyId);

    @Query("SELECT * FROM snapshots WHERE family_id = :familyId AND in_progress = 1")
    Snapshot queryInProgressSnapshotForFamilyNow(int familyId);

    @Query("SELECT * FROM snapshots WHERE family_id IS NULL AND in_progress = 1")
    LiveData<Snapshot> queryInProgressSnapshotForNewFamily();

    @Query("SELECT * FROM snapshots WHERE family_id IS NULL AND in_progress = 1")
    Snapshot queryInProgressSnapshotForNewFamilyNow();

    @Query("DELETE FROM snapshots WHERE id = :id AND in_progress = 1")
    int deleteInProgressSnapshot(int id);

    /**
     * Queries for all snapshots that only exist locally, which haven't been pushed to the
     * remote database and do not have a remote ID.
     * @return The pending snapshots.
     */
    @Query("SELECT * FROM snapshots WHERE remote_id IS NULL AND in_progress = 0")
    List<Snapshot> queryPendingFinishedSnapshots();


    @Query("SELECT * FROM snapshots WHERE remote_id = :remoteId")
    Snapshot queryRemoteSnapshotNow(long remoteId);

    @Insert(onConflict = FAIL)
    long insertSnapshot(Snapshot snapshot);

    @Update
    int updateSnapshot(Snapshot snapshot);

    @Query("DELETE FROM snapshots")
    int deleteAll();

    @Query("SELECT * FROM snapshots WHERE remote_id = :id")
    Snapshot querySnapshot(long id);


    @Query("DELETE FROM snapshots WHERE id = :id and in_progress = 0")
    int deleteSyncedSnapshot(int id);

}
