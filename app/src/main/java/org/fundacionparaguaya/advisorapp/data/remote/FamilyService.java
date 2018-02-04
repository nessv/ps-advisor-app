package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.FamilyIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * The service used to retrieve families from the remote database.
 */

public interface FamilyService {

    @GET("api/v1/families")
    Call<List<FamilyIr>> getFamilies(@Header("Authorization") String authorization);

    @POST("api/v1/families")
    Call<FamilyIr> postFamily(@Header("Authorization") String authorization, @Body FamilyIr family);
}
