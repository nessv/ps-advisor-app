package org.fundacionparaguaya.advisorapp.data.remote.ir;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.FamilyMember;

/**
 * The representation of a family member from the remote database.
 */

public class FamilyMemberIr {
    @SerializedName("personId")
    private int id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("gender")
    private String gender;
    @SerializedName("profileUrl")
    private String profileUrl;

    public FamilyMemberIr(int id, String firstName, String lastName, String gender, String profileUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.profileUrl = profileUrl;
    }

    public FamilyMember familyMember() {
        return new FamilyMember(firstName, lastName, profileUrl);
    }
}
