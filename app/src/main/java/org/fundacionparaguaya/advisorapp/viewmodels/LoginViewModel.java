package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;

/**
 * The view model exposing data for the login page.
 */

public class LoginViewModel extends ViewModel {
    private AuthenticationManager authManager;

    public LoginViewModel(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public AuthenticationManager getAuthManager() {
        return authManager;
    }
}


