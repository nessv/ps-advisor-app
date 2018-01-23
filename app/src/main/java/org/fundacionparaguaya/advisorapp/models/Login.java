package org.fundacionparaguaya.advisorapp.models;

/**
 * The login of a user.
 */

public class Login {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;

    public Login(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Login(String accessToken, String tokenType, int expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }
}
