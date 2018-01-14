package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A family is the entity that is being helped by the advisor. The family has snapshots of their situation
 * added when they take a survey. In the future, they will also have notes.
 */
@Entity(tableName = "families")
public class Family
{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    @Ignore
    private Location location;
    @Ignore
    private Collection<FamilyMember> members;

    public Family(long id, String name) {
        this(id, name, Location.UNKNOWN);
    }

    public Family(long id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.members = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public Collection<FamilyMember> getMembers() {
        return members;
    }

    public void addMember(FamilyMember member) {
        this.members.add(member);
    }

    public void removeMember(FamilyMember member) {
        this.members.remove(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Family family = (Family) o;

        if (getId() != family.getId()) return false;
        if (getName() != null ? !getName().equals(family.getName()) : family.getName() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(family.getLocation()) : family.getLocation() != null)
            return false;
        return getMembers() != null ? getMembers().equals(family.getMembers()) : family.getMembers() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getMembers() != null ? getMembers().hashCode() : 0);
        return result;
    }
}
