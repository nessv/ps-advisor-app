package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.FamilyMember;

/**
 * The intermediate representation of a family member from the remote database.
 */

public class FamilyMemberIr {
    @SerializedName("personId")
    long id;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("birthdate")
    String birthdate;
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("identificationType")
    String identificationType;
    @SerializedName("identificationNumber")
    String identificationNumber;
    @SerializedName("gender")
    String gender;
    @SerializedName("profileUrl")
    String profileUrl;
}
