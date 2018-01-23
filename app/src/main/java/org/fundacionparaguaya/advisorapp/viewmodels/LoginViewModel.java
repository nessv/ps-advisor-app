package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;

import java.util.concurrent.ExecutionException;

/**
 * The view model exposing data for the login page.
 */

public class LoginViewModel extends ViewModel {
    private AuthenticationManager authManager;

    public LoginViewModel(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public boolean login(String username, String password) {
        boolean successful = false;
        try {
            successful = authManager.login(username, password).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return successful;
    }

    /**
     * Attempts to login using the stored authentication token.
     * @return Whether a stored token was successfully authenticated.
     */
    public boolean login() {
        boolean successful = false;
        try {
            successful = authManager.login().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return successful;
    }
}


