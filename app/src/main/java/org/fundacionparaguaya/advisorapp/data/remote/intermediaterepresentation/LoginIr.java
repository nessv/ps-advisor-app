package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.Login;

/**
 * The intermediate representation of a login from the remote database.
 */

public class LoginIr {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("refresh_token")
    private String refreshToken;

    public LoginIr(String accessToken, String tokenType, int expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public Login login() {
        return new Login(accessToken, tokenType, expiresIn, refreshToken);
    }
}
