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
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "remote_id")
    private int remoteId;
    private String name;
    private String address;
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
        this(-1, name, address, location);
    }

    @Ignore
    public Family(int remoteId, String name, String address, Location location) {
        this.remoteId = remoteId;
        this.name = name;
        this.address = address;
        this.location = location;
    }

    public Family(int id, int remoteId, String name, String address, Location location, FamilyMember member) {
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.address = address;
        this.location = location;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FamilyMember getMember() {
        return member;
    }

    public String getAddress() {
        return address;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Family family = (Family) o;

        if (getId() != family.getId()) return false;
        if (getName() != null ? !getName().equals(family.getName()) : family.getName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(family.getAddress()) : family.getAddress() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(family.getLocation()) : family.getLocation() != null)
            return false;
        return getMember() != null ? getMember().equals(family.getMember()) : family.getMember() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getMember() != null ? getMember().hashCode() : 0);
        return result;
    }
}
