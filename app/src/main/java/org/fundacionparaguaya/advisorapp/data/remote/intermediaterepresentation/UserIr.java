package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

/**
 * The intermediate representation of a user from the remote database.
 */

public class UserIr {
    @SerializedName("userId")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("pass")
    private String password;
    @SerializedName("active")
    private boolean active;
}
