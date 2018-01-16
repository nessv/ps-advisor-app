package org.fundacionparaguaya.advisorapp.data.remote;

import retrofit2.Retrofit;

/**
 * The database interfacing with the remote server.
 */

public class RemoteDatabase {
    private FamilyService familyService;

    public RemoteDatabase(Retrofit retrofit) {
        familyService = retrofit.create(FamilyService.class);
    }

    public FamilyService familyService() {
        return familyService;
    }
}
