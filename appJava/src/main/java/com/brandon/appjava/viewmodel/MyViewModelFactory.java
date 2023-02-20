package com.brandon.appjava.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Provider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<ViewModel> mViewModelProvider;
    private final Class<? extends ViewModel> mViewModelClass;

    @Inject
    public MyViewModelFactory(Provider<ViewModel> viewModelProvider, Class<? extends ViewModel> mViewModelClass) {
        mViewModelProvider = viewModelProvider;
        this.mViewModelClass = mViewModelClass;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(mViewModelClass)) {
            return (T) mViewModelProvider.get();
        }
        throw new IllegalArgumentException("Brandon_Test ViewModel Class Not Found.");
    }
}
