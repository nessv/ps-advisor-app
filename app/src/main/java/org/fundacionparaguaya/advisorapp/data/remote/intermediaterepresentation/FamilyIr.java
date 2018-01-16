package org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation;

import com.google.gson.annotations.SerializedName;

import org.fundacionparaguaya.advisorapp.models.Family;

/**
 * The intermediate representation of the family from the remote database.
 */

public class FamilyIr {
    @SerializedName("familyId")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("person")
    private FamilyMemberIr member;
    @SerializedName("active")
    private boolean active;

    public FamilyIr(int id, String code, String name, FamilyMemberIr member, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.member = member;
        this.active = active;
    }

    public Family family() {
        return new Family(id, name, member.familyMember(), null, null);
    }
}
