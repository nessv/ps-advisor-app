package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * The service used to receive authentication information from the remote database.
 */

public interface AuthenticationService {

    @POST("oauth/token?grant_type=password")
    Call<LoginIr> loginWithPassword(
            @Header("Authorization") String authorization,
            @Query("username") String username,
            @Query("password") String password);

    @POST("oauth/token?grant_type=refresh_token")
    Call<LoginIr> loginWithRefreshToken(
            @Header("Authorization") String authorization,
            @Query("refresh_token") String refreshToken);
}
