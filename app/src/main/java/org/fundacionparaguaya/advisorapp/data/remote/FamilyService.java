package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.FamilyIr;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * The service used to retrieve families from the remote database.
 */

public interface FamilyService {

    @POST("oauth/token?grant_type=password")
    Call<LoginIr> login(
            @Query("username") String username,
            @Query("password") String password);

    @GET("api/v1/families")
    Call<List<FamilyIr>> getFamilies(@Header("Authentication") String authentication);
}
