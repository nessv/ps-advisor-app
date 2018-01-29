package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;

/**
 * The member of a family being advised.
 */

public class FamilyMember {
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "profile_url")
    private String profileUrl;

    public FamilyMember(String firstName, String lastName, String profileUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileUrl = profileUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyMember that = (FamilyMember) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
            return false;
        return profileUrl != null ? profileUrl.equals(that.profileUrl) : that.profileUrl == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (profileUrl != null ? profileUrl.hashCode() : 0);
        return result;
    }
}
