package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * A family is the entity that is being helped by the advisor. The family has snapshots of their situation
 * added when they take a survey. In the future, they will also have notes.
 */
@Entity(tableName = "families")
public class Family
{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private String uid;

    public Family(Long id, String name, String uid) {
        this.id = id;

        this.name = name;
        this.uid = uid;
    }

    public Long getId() { return this.id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getUid() { return this.uid; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Family family = (Family) o;

        if (getId() != null ? !getId().equals(family.getId()) : family.getId() != null)
            return false;
        if (getName() != null ? !getName().equals(family.getName()) : family.getName() != null)
            return false;
        return getUid() != null ? getUid().equals(family.getUid()) : family.getUid() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        return result;
    }
}
