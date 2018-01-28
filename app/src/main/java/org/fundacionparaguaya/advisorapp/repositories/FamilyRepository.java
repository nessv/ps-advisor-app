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
        familyDao.insertFamily(family);
    }

    public void deleteFamily(Family family) {
        familyDao.deleteFamily(family);
    }

    /**
     * Synchronizes the local families with the remote database.
     * @return Whether the sync was successful.
     */
    boolean sync() {
        boolean successful;
        successful = pushFamilies();
        if (successful) {
            successful = pullFamilies();
        }
        return successful;
    }

    private boolean pushFamilies() {
        List<Family> pending = familyDao.queryPendingFamilies();
        boolean success = true;

        // attempt to push each of the pending families
        for (Family family : pending) {
            try {
                Response<FamilyIr> response = familyService
                        .postFamily(authManager.getAuthenticationString(), IrMapper.mapFamily(family))
                        .execute();

                if (response.isSuccessful() || response.body() != null) {
                    // overwrite the pending family with the family from remote db
                    Family remoteFamily = IrMapper.mapFamily(response.body());
                    remoteFamily.setId(family.getId());
                    familyDao.updateFamily(remoteFamily);
                } else {
                    success = false;
                }
            } catch (IOException e) {
                Log.e(TAG, String.format("pushFamilies: Could not push family \"%s\"!", family.getName()), e);
                success = false;
            }
        }
        return success;
    }

    private boolean pullFamilies() {
        try {
            Response<List<FamilyIr>> response =
                    familyService.getFamilies(authManager.getAuthenticationString()).execute();

            if (!response.isSuccessful() || response.body() == null) {
                return false;
            }

            List<Family> families = IrMapper.mapFamilies(response.body());
            familyDao.insertFamilies(families.toArray(new Family[families.size()]));
        } catch (IOException e) {
            Log.e(TAG, "pullFamilies: Could not pull families!", e);
            return false;
        }
        return true;
    }
    //endregion
}
