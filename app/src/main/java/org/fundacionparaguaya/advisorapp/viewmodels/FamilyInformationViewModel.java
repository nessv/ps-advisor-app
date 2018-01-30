package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.models.*;
import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.repositories.SnapshotRepository;

import java.util.*;

import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Green;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Red;
import static org.fundacionparaguaya.advisorapp.models.IndicatorOption.Level.Yellow;


public class FamilyInformationViewModel extends ViewModel {

    private FamilyRepository mFamilyRepository;
    private SnapshotRepository mSnapshotRespository;

    private LiveData<Family> currentFamily;

    final private MutableLiveData<Snapshot> mSelectedSnapshot = new MutableLiveData<>();

    /**Temp snapshot list.. TODO @blhylak replace once snapshot repository works
     *
     */
    List<Snapshot> dummySnapshotList = new ArrayList<Snapshot>();

    private LiveData<List<Snapshot>> mSnapshots;

    public  FamilyInformationViewModel(FamilyRepository familyRepository, SnapshotRepository snapshotRespository){
        mFamilyRepository = familyRepository;
        mSnapshotRespository = snapshotRespository;

      //  addDummySnapshots();
    }

    //LiveData object of the indicators in a snapshot

    final private LiveData<SortedMap<IndicatorQuestion, IndicatorOption>> mIndicatorsForSelected = Transformations.map(mSelectedSnapshot, selected ->
    {

        if(selected==null)
        {
            return null;
        }
        else
        {
            SortedMap<IndicatorQuestion, IndicatorOption> sortedMap = new TreeMap<>();
            sortedMap.putAll(selected.getIndicatorResponses());
            return sortedMap;
        }
    });

    private void addDummySnapshots()
    {
        //DD-MM-YYYY

        /*
        Snapshot s1 = new Snapshot(-1, -1 ,-1, null, null, new HashMap<>());
        s1.setDate("01-01-2017");
        List<IndicatorQuestion> indicatorQuestions = new ArrayList<>();
        List<IndicatorOption> indicatorOptions = new ArrayList<>();
        indicatorOptions.add(new IndicatorOption("Has a stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-3.jpg", Green));
        indicatorOptions.add(new IndicatorOption("Has no stove.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-2.jpg", Yellow));
        indicatorOptions.add(new IndicatorOption("Has no kitchen.", "https://s3.us-east-2.amazonaws.com/fp-psp-images/21-1.jpg", Red));
        Indicator indicator = new Indicator("Kitchen Health", "Home", indicatorOptions);
        indicatorQuestions.add(new IndicatorQuestion(indicator));

        Survey survey = new Survey(1, 1L, null, null, indicatorQuestions);

        s1.response(survey.getIndicatorQuestions().get(0), survey.getIndicatorQuestions().get(0).getOptions().get(1));


        dummySnapshotList.add(s1);

        s1 = new Snapshot(-1, -1 ,-1, null, null, new HashMap<>());
        s1.setDate("07-04-2017");
        s1.response(survey.getIndicatorQuestions().get(0), survey.getIndicatorQuestions().get(0).getOptions().get(0));
        dummySnapshotList.add(s1);

        s1 = new Snapshot(-1, -1 ,-1, null, null, new HashMap<>());
        s1.setDate("03-09-2017");
        s1.response(survey.getIndicatorQuestions().get(0), survey.getIndicatorQuestions().get(0).getOptions().get(2));
        dummySnapshotList.add(s1);


        s1 = new Snapshot(-1, -1 ,-1, null, null, new HashMap<>());
        s1.setDate("29-01-2018");
        s1.response(survey.getIndicatorQuestions().get(0), survey.getIndicatorQuestions().get(0).getOptions().get(2));
        dummySnapshotList.add(s1);*/
    }



    /**
     * Sets the current family for this view model and returns the LiveData representation
     * @param id family id for this view model
     * @return current family selected
     */
    public LiveData<Family> setFamily(int id){
        currentFamily = mFamilyRepository.getFamily(id);

        mSnapshots = Transformations.switchMap(currentFamily, currentFamily -> {
            if(currentFamily==null)
            {
                return null;
            }
            else return mSnapshotRespository.getSnapshots(currentFamily);
        });

        return currentFamily;
    }

    /**
     * Returns the indicators for the selected snapshot. Will update when the selected snapshot is changed
     * @return
     */
    public LiveData<SortedMap<IndicatorQuestion, IndicatorOption>> getSnapshotIndicators()
    {
       return mIndicatorsForSelected;
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
