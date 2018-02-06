package org.fundacionparaguaya.advisorapp.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.IrMapper;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An interceptor which injects authentication headers into outgoing requests.
 */

public class AuthenticationInterceptor implements Interceptor {
    private static final String TAG = "AuthInterceptor";
    private static final String AUTH_KEY = "Basic YmFyQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=";

    private AuthenticationManager mAuthManager;

    public AuthenticationInterceptor(AuthenticationManager authManager) {
        this.mAuthManager = authManager;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("Authorization", mAuthManager.getAuthenticationString())
                .build();

        Response response = chain.proceed(request);

        if (response.code() == 401) {
            Log.i(TAG, "intercept: Got an unauthorized message, refreshing the token.");
            refreshToken();
            request = chain.request().newBuilder()
                    .header("Authorization", mAuthManager.getAuthenticationString())
                    .build();
            response = chain.proceed(request);
            if (response.code() == 401) {
                Log.w(TAG, "intercept: Refresh failed, invalidating the login!");
                mAuthManager.getUser().setLogin(null);
            }
        }

        return response;
    }

    private void refreshToken() {
        try {
            User user = mAuthManager.getUser();
            retrofit2.Response<LoginIr> response = mAuthManager.getAuthService()
                    .loginWithRefreshToken(
                            AUTH_KEY,
                            user.getLogin().getRefreshToken()).execute();
            Login login = IrMapper.mapLogin(response.body());
            user.setLogin(login);
            user.setEnabled(true);
            mAuthManager.saveRefreshToken();
        } catch (IOException e) {
            Log.e(TAG, "refreshToken: Could not refresh the token!", e);
        }
    }


}
