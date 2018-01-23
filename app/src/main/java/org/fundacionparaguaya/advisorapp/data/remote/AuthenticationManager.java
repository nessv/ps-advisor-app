package org.fundacionparaguaya.advisorapp.data.remote;

import android.app.Application;
import android.content.SharedPreferences;

import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.User;

import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A manager for all things authentication related.
 */

@Singleton
public class AuthenticationManager {
    private static final String PREFS_AUTHENTICATION = "auth";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";

    private User user;
    private SharedPreferences preferences;
    private AuthenticationService authService;

    public AuthenticationManager(Application application, AuthenticationService authService) {
        this.authService = authService;

        preferences = application.getApplicationContext()
                .getSharedPreferences(PREFS_AUTHENTICATION, MODE_PRIVATE);

        String refreshToken = preferences.getString(KEY_REFRESH_TOKEN, null);
        this.user = new User(new Login(refreshToken));
    }

    public User getUser() {
        return user;
    }

    public AuthenticationService getAuthService() {
        return authService;
    }

    public String getAuthenticationString() {
        return user.getLogin().getAuthenticationString();
    }

    public boolean hasRefreshToken() {
        return user.getLogin().getRefreshToken() != null;
    }

    public void saveRefreshToken() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_REFRESH_TOKEN, user.getLogin().getRefreshToken());
        editor.apply();
    }
}
