package org.fundacionparaguaya.advisorapp.data.remote;

import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.User;

import java.io.IOException;

import retrofit2.Response;

/**
 * A task for logging a user into the remote database. This will attempt to login, and will return
 * whether the login was successful.
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private AuthenticationManager manager;

    public UserLoginTask(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Response<LoginIr> response = null;
            User user = manager.getUser();
            if (user.getLogin().getRefreshToken() != null) {
                 response = manager.getAuthService()
                        .loginWithRefreshToken(
                                "Basic YmFyQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=",
                                user.getLogin().getRefreshToken()).execute();
            }

            if (!wasSuccessful(response) && user.getPassword() != null) {
                response = manager.getAuthService()
                        .loginWithPassword(
                                "Basic YmFyQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=",
                                user.getUsername(), user.getPassword()).execute();
            }

            if (!wasSuccessful(response)) {
                return false;
            }

            Login login = response.body().login();
            user.setLogin(login);
            user.setEnabled(true);
            manager.saveRefreshToken();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private <T> boolean wasSuccessful(Response<T> response) {
        return response != null && response.isSuccessful() && response.body() != null;
    }
}
