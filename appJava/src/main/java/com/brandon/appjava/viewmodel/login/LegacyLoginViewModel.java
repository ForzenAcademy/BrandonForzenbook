package com.brandon.appjava.viewmodel.login;

import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.appjava.viewmodel.login.LoginUiStates.IDLE;

import android.net.ConnectivityManager;

import com.brandon.appjava.domain.login.LoginUseCaseJava;

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

    private final BehaviorSubject<LoginUiStateJava> _uiState = BehaviorSubject.createDefault(new LoginUiStateJava("", NO_INPUT_ERROR, NO_CONNECTION_ERROR, false, IDLE));

    public @NonNull Flowable<LoginUiStateJava> getState() {
        return _uiState.toFlowable(BackpressureStrategy.LATEST);
    }

    @Inject
    public LegacyLoginViewModel(LoginUseCaseJava loginUseCaseJava, ConnectivityManager connectivityManager) {
        this.loginUseCaseJava = loginUseCaseJava;
        this.connectivityManager = connectivityManager;
    }

    public void emailChanged(String email) {
        _uiState.getValue().setEmail(email);
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
    protected void setState(LoginUiStateJava newState) {
        _uiState.onNext(newState);
    }

}
