package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;
import org.fundacionparaguaya.advisorapp.repositories.SurveyRepository;

/**
 * Created by Kodey Converse on 1/20/2018.
 */

public class EXAMPLEViewModel extends ViewModel {
    private FamilyRepository mFamilyRepository;
    private SurveyRepository mSurveyRepository;

    public EXAMPLEViewModel(FamilyRepository familyRepository, SurveyRepository surveyRepository) {
        this.mFamilyRepository = familyRepository;
        this.mSurveyRepository = surveyRepository;
    }
}
