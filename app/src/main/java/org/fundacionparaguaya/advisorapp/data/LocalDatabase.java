package org.fundacionparaguaya.advisorapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.FamilyMember;

/**
 * The database storing a local cache of data for the user.
 */
@Database(entities = {Family.class, FamilyMember.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract FamilyDao familyDao();
    public abstract FamilyMemberDao familyMemberDao();
}
