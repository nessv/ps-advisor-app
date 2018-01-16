package org.fundacionparaguaya.advisorapp.sync;

import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.remote.FamilyService;
import org.fundacionparaguaya.advisorapp.data.remote.ir.FamilyIr;
import org.fundacionparaguaya.advisorapp.models.Family;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Response;

/**
 * A task for synchronizing tasks.
 */

public class FamilySynchronizeTask extends AsyncTask<Void, Void, Boolean> {
    private FamilyDao familyDao;
    private FamilyService familyService;

    public FamilySynchronizeTask(FamilyDao familyDao, FamilyService familyService) {
        this.familyDao = familyDao;
        this.familyService = familyService;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Response<List<FamilyIr>> response = familyService.getFamilies().execute();

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
