package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * A family is the entity that is being helped by the advisor.
 */

@Entity(tableName = "families",
        indices={@Index(value="remote_id", unique=true)})
public class Family {
    // TODO: use same constraints on remote database for local database
    // TODO: add missing fields (code)
    // TODO: create builder notation
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "remote_id")
    private Long remoteId;
    private String name;
    private String address;
    @ColumnInfo(name="is_active")
    private boolean isActive;
    @Embedded
    private Location location;
    @Embedded(prefix = "family_member_")
    private FamilyMember member;

    @Ignore
    public Family(String name) {
        this(name, "", Location.UNKNOWN);
    }

    @Ignore
    public Family(String name, String address, Location location) {
        this(null, name, address, location, null, true);
    }

    @Ignore
    public Family(Long remoteId, String name, String address, Location location, FamilyMember member, boolean isActive) {
        this(0, remoteId, name, address, location, member, isActive);
    }

    public Family(int id, Long remoteId, String name, String address, Location location, FamilyMember member, boolean isActive) {
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.address = address;
        this.location = location;
        this.member = member;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {this.location = location; }

    public FamilyMember getMember() {
        return member;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Family family = (Family) o;

        if (id != family.id) return false;
        if (isActive != family.isActive) return false;
        if (remoteId != null ? !remoteId.equals(family.remoteId) : family.remoteId != null)
            return false;
        if (name != null ? !name.equals(family.name) : family.name != null) return false;
        if (address != null ? !address.equals(family.address) : family.address != null)
            return false;
        if (location != null ? !location.equals(family.location) : family.location != null)
            return false;
        return member != null ? member.equals(family.member) : family.member == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (remoteId != null ? remoteId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        return result;
    }
}
