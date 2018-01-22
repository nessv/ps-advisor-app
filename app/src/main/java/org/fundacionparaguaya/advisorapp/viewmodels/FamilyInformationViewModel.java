package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;

import java.util.concurrent.ExecutionException;

/**
 * Created by Mone Elokda on 1/20/2018.
 */

public class FamilyInformationViewModel extends ViewModel {
    private FamilyRepository mFamilyRepository;

    public  FamilyInformationViewModel(FamilyRepository familyRepository){
        mFamilyRepository = familyRepository;
    }

    public LiveData<Family> getFamily(int id){ return mFamilyRepository.getFamily(id); }

   /* public boolean syncFamily() {
        try {
            return mFamilyRepository.sync().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sync() { mFamilyRepository.sync().execute(); }*/


}
