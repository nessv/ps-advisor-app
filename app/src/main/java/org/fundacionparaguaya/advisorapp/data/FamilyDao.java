package org.fundacionparaguaya.advisorapp.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.fundacionparaguaya.advisorapp.models.Family;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * The access utility for retrieving families from the local database.
 */
@Dao
public interface FamilyDao {
    @Query("SELECT * FROM families")
    LiveData<List<Family>> queryFamilies();

    @Query("SELECT * FROM families WHERE id = :id")
    LiveData<Family> queryFamily(Long id);

    @Insert(onConflict = REPLACE)
    Long insertFamily(Family family);

    @Update
    void updateFamily(Family family);

    @Delete
    void deleteFamily(Family family);
}
