package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;

import java.util.concurrent.ExecutionException;

/**
 * The view model exposing data for the login page.
 */

public class LoginViewModel extends ViewModel {
    private FamilyRepository mFamilyRepository;

    public LoginViewModel(FamilyRepository familyRepository) {
        this.mFamilyRepository = familyRepository;
    }

    public boolean login(String username, String password) {
        try {
            return mFamilyRepository.login(username, password).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}


