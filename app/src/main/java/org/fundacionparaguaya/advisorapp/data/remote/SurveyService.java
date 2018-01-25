package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * The service used to retrieve surveys from the remote database.
 */

public interface SurveyService {

    @GET("api/v1/surveys")
    Call<List<SurveyIr>> getSurveys(@Header("Authorization") String authorization);
}
