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

public class UserLoginTask extends AsyncTask<User, Void, Login> {
    private FamilyService familyService;

    public UserLoginTask(FamilyService familyService) {
        this.familyService = familyService;
    }

    @Override
    protected Login doInBackground(User... users) {
        Login login;
        try {
            User user = users[0];
            Response<LoginIr> response = familyService
                    .login(user.getUsername(), user.getPassword()).execute();

            if (!response.isSuccessful()) {
                return null;
            }

            if (response.body() == null) {
                return null;
            }

            login = response.body().login();
            user.setLogin(login);
        } catch (IOException e) {
            return null;
        }
        return login;
    }
}
