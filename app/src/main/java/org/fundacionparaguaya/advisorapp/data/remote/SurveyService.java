package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.SurveyIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The service used to retrieve surveys from the remote database.
 */

public interface SurveyService {

    @GET("surveys")
    Call<List<SurveyIr>> getSurveys();
}
