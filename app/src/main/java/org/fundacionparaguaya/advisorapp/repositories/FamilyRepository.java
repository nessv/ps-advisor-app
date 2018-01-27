package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.data.remote.FamilyService;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.FamilyIr;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.IrMapper;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * The utility for the storage of families and their members.
 */
public class FamilyRepository {
    private static final String TAG = "FamilyRepository";

    private final FamilyDao familyDao;
    private final FamilyService familyService;
    private final AuthenticationManager authManager;

    @Inject
    public FamilyRepository(FamilyDao familyDao,
                            FamilyService familyService,
                            AuthenticationManager authManager) {
        this.familyDao = familyDao;
        this.familyService = familyService;
        this.authManager = authManager;
    }

    //region Family
    public LiveData<List<Family>> getFamilies() {
        return familyDao.queryFamilies();
    }

    public LiveData<Family> getFamily(int id) {
        return familyDao.queryFamily(id);
    }

    public void saveFamily(Family family) {
        int rowCount = familyDao.updateFamily(family);
        if (rowCount == 0) { // didn't already exist
            familyDao.insertFamily(family);
        }
    }

    public void deleteFamily(Family family) {
        familyDao.deleteFamily(family);
    }

    /**
     * Synchronizes the local families with the remote database.
     * @return Whether the sync was successful.
     */
    boolean sync() {
        try {
            Response<List<FamilyIr>> response =
                    familyService.getFamilies(authManager.getAuthenticationString()).execute();

            if (!response.isSuccessful()) {
                return false;
            }

            if (response.body() == null) {
                return false;
            }

            List<Family> families = IrMapper.mapFamilies(response.body());
            familyDao.insertFamilies(families.toArray(new Family[families.size()]));
        } catch (IOException e) {
            Log.e(TAG, "sync: Could not sync the family repository!", e);
            return false;
        }
        return true;
    }
    //endregion
}
