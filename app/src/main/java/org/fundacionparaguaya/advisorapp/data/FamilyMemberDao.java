package org.fundacionparaguaya.advisorapp.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.fundacionparaguaya.advisorapp.models.FamilyMember;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * The access utility for retrieving family members from the local database.
 */
@Dao
public interface FamilyMemberDao {
    @Query("SELECT * FROM family_members WHERE family_id = :familyId")
    LiveData<List<FamilyMember>> queryFamilyMembers(int familyId);

    @Query("SELECT * FROM family_members WHERE id = :id")
    LiveData<FamilyMember> queryFamilyMember(int id);

    @Insert(onConflict = REPLACE)
    long insertFamilyMember(FamilyMember member);

    @Update
    int updateFamilyMember(FamilyMember member);

    @Delete
    int deleteFamilyMember(FamilyMember member);
}
