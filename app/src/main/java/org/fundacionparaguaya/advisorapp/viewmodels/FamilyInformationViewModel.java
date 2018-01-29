package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.*;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FamilyInformationViewModel extends ViewModel {

    private FamilyRepository mFamilyRepository;
    private LiveData<Family> currentFamily;

    final private MutableLiveData<Snapshot> mSelectedSnapshot = new MutableLiveData<>();

    private LiveData<List<Snapshot>> mSnapshots = Transformations.map(currentFamily, currentFamily -> {
        if(currentFamily==null)
        {
            return null;
        }
        else return Arrays.asList(new Snapshot(currentFamily, null));
    });

    private LiveData<Map<IndicatorQuestion, IndicatorOption>> mIndicatorsForSelected = Transformations.map(mSelectedSnapshot, selected ->
    {
        //group by dimension and sort alphabetically, then
        if(selected==null)
        {
            return null;
        }
        else return selected.getIndicatorResponses();
    });

    public  FamilyInformationViewModel(FamilyRepository familyRepository){
        mFamilyRepository = familyRepository;
    }

    /**
     * Sets the current family for this view model and returns the LiveData representation
     * @param id family id for this view model
     * @return current family selected
     */
    public LiveData<Family> setFamily(int id){
        currentFamily = mFamilyRepository.getFamily(id);
        return currentFamily;
    }

    /**Gets the current family that's been set by setFamily**/
    public LiveData<Family> getCurrentFamily()
    {
        if(currentFamily == null)
        {
            throw new IllegalStateException("setFamily must be called in ViewModel before getCurrentFamily");
        }
        else return currentFamily;
    }

    public LiveData<List<Snapshot>> getSnapshots()
    {
        return mSnapshots;
    }

    public void setSelectedSnapshot(Snapshot s)
    {
        mSelectedSnapshot.setValue(s);
    }
}
