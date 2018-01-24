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

    public FamilyIr(int id, String code, String name, FamilyMemberIr member, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.member = member;
        this.active = active;
    }

    public Family family() {
        return new Family(id, name, null, null, null);
    }
}
