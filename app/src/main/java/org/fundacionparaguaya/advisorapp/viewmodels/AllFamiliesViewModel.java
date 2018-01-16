package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.Family;
import org.fundacionparaguaya.advisorapp.models.FamilyMember;
import org.fundacionparaguaya.advisorapp.rapositories.FamilyRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class AllFamiliesViewModel extends ViewModel {

    private MutableLiveData<List<Family>> families;
    private MutableLiveData<ArrayList<Family>> mCurrentFamilies;

    public LiveData<List<Family>> getFamily(){
        if(families == null){
            families = new MutableLiveData<List<Family>>();
        }
        return families;
    }

    private AllFamiliesViewModel(){
        ArrayList<Family> dummyList = new ArrayList<>();
        mCurrentFamilies.setValue(dummyList);
    }


    private ArrayList<Family> loadfamilies(){
        ArrayList<Family> dummyList = new ArrayList<>();
        String[] familyNames = {"Elokda", "Hylak", "Tacescu", "Converse"};

        for(String name: familyNames){

            /*Family f = new Family()
            dummyList.add(f);
*/
        }

        return dummyList;
    }

}


