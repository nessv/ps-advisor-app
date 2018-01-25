package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.FamilyMember;

/**
 * The intermediate representation of a family member from the remote database.
 */

public class FamilyMemberIr {
    @SerializedName("personId")
    int id;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("gender")
    String gender;
    @SerializedName("profileUrl")
    String profileUrl;

    public FamilyMember familyMember() {
        return new FamilyMember(firstName, lastName, profileUrl);
    }
}
