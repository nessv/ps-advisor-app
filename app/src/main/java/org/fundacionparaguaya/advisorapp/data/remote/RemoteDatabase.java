package org.fundacionparaguaya.advisorapp.data.remote;

import retrofit2.Retrofit;

/**
 * The database interfacing with the remote server.
 */

public class RemoteDatabase {
    private AuthenticationService authService;
    private FamilyService familyService;

    public RemoteDatabase(Retrofit retrofit) {
        authService = retrofit.create(AuthenticationService.class);
        familyService = retrofit.create(FamilyService.class);
    }

    public AuthenticationService authService() { return authService; }
    public FamilyService familyService() {
        return familyService;
    }
}
