package org.fundacionparaguaya.advisorapp.data.remote;

import org.fundacionparaguaya.advisorapp.data.remote.ir.FamilyIr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The service used to retrieve families from the remote database.
 */

public interface FamilyService {

    @GET("families")
    Call<List<FamilyIr>> getFamilies();
}
