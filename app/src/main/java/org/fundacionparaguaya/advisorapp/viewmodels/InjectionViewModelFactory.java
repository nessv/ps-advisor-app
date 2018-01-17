package org.fundacionparaguaya.advisorapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import org.fundacionparaguaya.advisorapp.repositories.FamilyRepository;

/**
 * A custom view model factory which will inject view models with the correct dependencies.
 */

public class InjectionViewModelFactory implements ViewModelProvider.Factory {
    private final FamilyRepository familyRepository;

    public InjectionViewModelFactory(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AllFamiliesViewModel.class))
            return (T) new AllFamiliesViewModel(familyRepository);
        else if (modelClass.isAssignableFrom(LoginViewModel.class))
            return (T) new LoginViewModel(familyRepository);
        else
            throw new IllegalArgumentException("The view model was not found!");
    }
}