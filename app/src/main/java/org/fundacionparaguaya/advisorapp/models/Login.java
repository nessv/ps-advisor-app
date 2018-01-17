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
}
