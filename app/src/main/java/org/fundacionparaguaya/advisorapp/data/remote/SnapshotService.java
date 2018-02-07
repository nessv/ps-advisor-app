package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SnapshotIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * The service used to retrieve snapshot from the remote database.
 */

public interface SnapshotService {

    @GET("snapshots")
    Call<List<SnapshotIr>> getSnapshots(
            @Query("survey_id") long surveyId,
            @Query("family_id") long familyId);

    @POST("snapshots")
    Call<SnapshotIr> postSnapshot(
            @Body SnapshotIr snapshotIr);
}
