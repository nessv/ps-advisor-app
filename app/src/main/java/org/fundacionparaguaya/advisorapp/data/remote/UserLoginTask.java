package org.fundacionparaguaya.advisorapp.data.remote;

import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.User;

import java.io.IOException;

import retrofit2.Response;

/**
 * A task for logging a user into the remote database. This will attempt to login, and will return
 * a Login
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private FamilyService familyService;
    private User user;

    public UserLoginTask(FamilyService familyService, User user) {
        this.familyService = familyService;
        this.user = user;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Response<LoginIr> response = familyService
                    .login(user.getUsername(), user.getPassword()).execute();

            if (!response.isSuccessful()) {
                return false;
            }

            if (response.body() == null) {
                return false;
            }

            Login login = response.body().login();
            user.setLogin(login);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
