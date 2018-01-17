package org.fundacionparaguaya.advisorapp.models;

/**
 * The login of a user.
 */

public class Login {
    private String accessToken;
    private String tokenType;
    private int expiresIn;

    public Login(String accessToken, String tokenType, int expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAuthenticationString() {
        return tokenType + " " + accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
