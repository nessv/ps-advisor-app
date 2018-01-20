package org.fundacionparaguaya.advisorapp.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.fundacionparaguaya.advisorapp.data.local.FamilyDao;
import org.fundacionparaguaya.advisorapp.data.remote.FamilyService;
import org.fundacionparaguaya.advisorapp.data.remote.FamilySynchronizeTask;
import org.fundacionparaguaya.advisorapp.data.remote.UserLoginTask;
import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.User;

import java.util.List;

import javax.inject.Inject;

/**
 * The utility for the storage of families and their members.
 */
public class FamilyRepository {
    private final FamilyDao familyDao;
    private final FamilyService familyService;

    private User user;

    @Inject
    public FamilyRepository(FamilyDao familyDao, FamilyService familyService) {
        this.familyDao = familyDao;
        this.familyService = familyService;
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

    public AsyncTask<Void, Void, Boolean> login(String username, String password) {
        user = new User(username, password, true);
        return new UserLoginTask(familyService, user);
    }
    /**
     * A task which will pull families from the remote database and synchronize them with the
     * local database.
     * @return A new async task to be executed.
     */
    public AsyncTask<Void, Void, Boolean> sync() {
        return new FamilySynchronizeTask(familyDao, familyService, user.getLogin());
    }
    //endregion
}
