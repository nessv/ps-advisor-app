package org.fundacionparaguaya.advisorapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * The member of a family being advised.
 */

@Entity(tableName = "family_members",
        indices = @Index("family_id") ,
        foreignKeys = @ForeignKey(entity = Family.class,
                parentColumns = { "id" },
                childColumns = { "family_id"},
                onUpdate = CASCADE,
                onDelete = CASCADE))
public class FamilyMember {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "family_id")
    private int familyId;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;

    public FamilyMember(int id, int familyId, String firstName, String lastName) {
        this.id = id;
        this.familyId = familyId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public int getFamilyId() {
        return familyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
