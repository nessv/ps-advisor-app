package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;

import java.util.List;

/**
 * The view model exposing data for the the all families page.
 */

public class AllFamiliesViewModel extends ViewModel {
    private FamilyRepository mFamilyRepository;

    public AllFamiliesViewModel(FamilyRepository familyRepository) {
        this.mFamilyRepository = familyRepository;
    }

    public LiveData<List<Family>> getFamilies(){
        return mFamilyRepository.getFamilies();
    }
}


