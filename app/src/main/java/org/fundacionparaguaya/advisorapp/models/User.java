package org.fundacionparaguaya.advisorapp.models;

/**
 * A user which can use the application.
 */

public class User {
    private String username;
    private String password;
    private boolean enabled;
    private Login login;

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
