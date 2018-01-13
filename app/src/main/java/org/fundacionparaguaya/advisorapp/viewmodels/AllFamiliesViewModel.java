package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.Family;

import java.util.List;

/**
 * Created by Mone Elokda on 1/13/2018.
 */

public class AllFamiliesViewModel extends ViewModel {
    private MutableLiveData<List<Family>> families;
    public LiveData<List<Family>> getFamily(){
        if(families == null){
            families = new MutableLiveData<List<Family>>();
        }
        return families;
    }

    private void loadfamilies(){
        
    }

}
