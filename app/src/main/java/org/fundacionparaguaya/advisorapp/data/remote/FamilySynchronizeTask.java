package org.fundacionparaguaya.advisorapp.data.remote;

import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.FamilyIr;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Response;

/**
 * A task for synchronizing families from the remote database to the local one. This will
 * grab families from the remote database and insert them into the local one, asynchronously.
 */

public class FamilySynchronizeTask extends AsyncTask<Void, Void, Boolean> {
    private FamilyDao familyDao;
    private FamilyService familyService;
    private AuthenticationManager authManager;

    public FamilySynchronizeTask(FamilyDao familyDao,
                                 FamilyService familyService,
                                 AuthenticationManager authManager) {
        this.familyDao = familyDao;
        this.familyService = familyService;
        this.authManager = authManager;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Response<List<FamilyIr>> response =
                    familyService.getFamilies(authManager.getAuthenticationString()).execute();

            if (!response.isSuccessful()) {
                return false;
            }

            if (response.body() == null) {
                return false;
            }

            List<Family> families = new LinkedList<>();
            for (FamilyIr ir : response.body()) {
                families.add(ir.family());
            }
            familyDao.insertFamilies(families.toArray(new Family[families.size()]));

        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
