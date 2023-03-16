package com.brandon.loginmodulejava.viewmodel;


import android.content.Context;
import android.net.ConnectivityManager;

import com.brandon.corejava.navigation.LegacyNavigationJava;
import com.brandon.loginmodulejava.domain.LoginUseCaseJava;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class LegacyLoginViewModel extends LoginViewModel {
    private final LoginUseCaseJava loginUseCaseJava;
    private final ConnectivityManager connectivityManager;
    private final LegacyNavigationJava navigation;

    private final BehaviorSubject<LoginUiStateJava> uiState = BehaviorSubject.createDefault(new LoginUiStateJava());

    public @NonNull Flowable<LoginUiStateJava> getState() {
        return uiState.toFlowable(BackpressureStrategy.LATEST);
    }

    @Inject
    public LegacyLoginViewModel(LoginUseCaseJava loginUseCaseJava, ConnectivityManager connectivityManager, LegacyNavigationJava navigation) {
        this.loginUseCaseJava = loginUseCaseJava;
        this.connectivityManager = connectivityManager;
        this.navigation = navigation;
    }

    public void emailChanged(String email) {
        uiState.getValue().setEmail(email);
    }

    public void notificationDialogOpened(boolean isOpen) {
        uiState.getValue().setConnectionDialogOpen(isOpen);
    }

    @Override
    protected LoginUseCaseJava loginUseCaseJava() {
        return loginUseCaseJava;
    }

    @Override
    protected ConnectivityManager connectivityManager() {
        return connectivityManager;
    }

    @Override
    public void navigateToCreateAccount(Context context) {
        navigation.navigateToCreateAccount(context);
    }

    @Override
    public void navigateToLandingScreen(Context context) {
        navigation.navigateToLandingPage(context);
    }

    @Override
    protected void setState(LoginUiStateJava newState) {
        uiState.onNext(newState);
    }

}
