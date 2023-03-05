package com.brandon.createaccountjava.viewmodel;


import android.content.Context;
import android.net.ConnectivityManager;

import com.brandon.corejava.navigation.LegacyNavigationJava;
import com.brandon.createaccountjava.domain.CreateUserUseCaseJava;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class LegacyCreateAccountViewModel extends CreateAccountViewModel {
    private final CreateUserUseCaseJava createUserUseCaseJava;
    private final ConnectivityManager connectivityManager;
    private final LegacyNavigationJava navigation;

    private final BehaviorSubject<CreateAccountUiStateJava> uiState
            = BehaviorSubject.createDefault(new CreateAccountUiStateJava());

    public @NonNull Flowable<CreateAccountUiStateJava> getState() {
        return uiState.toFlowable(BackpressureStrategy.LATEST);
    }

    @Inject
    public LegacyCreateAccountViewModel(
            CreateUserUseCaseJava createUserUseCaseJava,
            ConnectivityManager connectivityManager,
            LegacyNavigationJava navigation
    ) {
        this.createUserUseCaseJava = createUserUseCaseJava;
        this.connectivityManager = connectivityManager;
        this.navigation = navigation;
    }

    public void firstNameChanged(String name) {
        uiState.getValue().setFirstName(name);
    }

    public void lastNameChanged(String name) {
        uiState.getValue().setLastName(name);
    }

    public void emailChanged(String email) {
        uiState.getValue().setEmail(email);
    }

    public void dateChanged(String date) {
        uiState.getValue().setDate(date);
    }

    public void locationChanged(String location) {
        uiState.getValue().setLocation(location);
    }

    public void datePickerOpened(boolean isOpen) {
        uiState.getValue().setDateDialogOpen(isOpen);
    }

    public void notificationDialogOpened(boolean isOpen) {
        uiState.getValue().setConnectionDialogOpen(isOpen);
    }


    @Override
    protected CreateUserUseCaseJava createUserUseCaseJava() {
        return createUserUseCaseJava;
    }

    @Override
    public void navigateToLogin(Context context) {
        navigation.navigateToLogin(context);
    }

    @Override
    protected ConnectivityManager connectivityManager() {
        return connectivityManager;
    }

    @Override
    protected void setState(CreateAccountUiStateJava newState) {
        uiState.onNext(newState);
    }
}
