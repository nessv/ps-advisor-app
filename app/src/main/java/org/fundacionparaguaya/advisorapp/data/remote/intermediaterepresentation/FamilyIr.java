package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.Family;

/**
 * The intermediate representation of the family from the remote database.
 */

public class FamilyIr {
    @SerializedName("familyId")
    int id;
    @SerializedName("code")
    String code;
    @SerializedName("name")
    String name;
    @SerializedName("person")
    FamilyMemberIr member;
    @SerializedName("active")
    boolean active;
}
