package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SnapshotIr;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * The service used to retrieve snapshot from the remote database.
 */

public interface SnapshotService {

    @GET("api/v1/snapshots")
    Call<List<SnapshotIr>> getSnapshots(
            @Header("Authorization") String authorization,
            @Query("survey_id") long surveyId,
            @Query("family_id") long familyId);
}
