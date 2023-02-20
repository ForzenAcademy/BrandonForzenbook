package com.brandon.appjava.viewmodel.createaccount;

import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.IDLE;

import android.net.ConnectivityManager;

import com.brandon.appjava.domain.user.CreateUserUseCaseJava;

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

    private final BehaviorSubject<CreateAccountUiStateJava> _uiState = BehaviorSubject.createDefault(new CreateAccountUiStateJava(IDLE, NO_CONNECTION_ERROR, "", "", "", "", "", NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR));

    public @NonNull Flowable<CreateAccountUiStateJava> getState() {
        return _uiState.toFlowable(BackpressureStrategy.LATEST);
    }

    @Inject
    public LegacyCreateAccountViewModel(CreateUserUseCaseJava createUserUseCaseJava, ConnectivityManager connectivityManager) {
        this.createUserUseCaseJava = createUserUseCaseJava;
        this.connectivityManager = connectivityManager;
    }

    public void firstNameChanged(String name) {
        _uiState.getValue().setFirstName(name);
    }

    public void lastNameChanged(String name) {
        _uiState.getValue().setLastName(name);
    }

    public void emailChanged(String email) {
        _uiState.getValue().setEmail(email);
    }

    public void dateChanged(String date) {
        _uiState.getValue().setDate(date);
    }

    public void locationChanged(String location) {
        _uiState.getValue().setLocation(location);
    }


    @Override
    protected CreateUserUseCaseJava createUserUseCaseJava() {
        return createUserUseCaseJava;
    }

    @Override
    protected ConnectivityManager connectivityManager() {
        return connectivityManager;
    }

    @Override
    protected void setState(CreateAccountUiStateJava newState) {
        _uiState.onNext(newState);
    }
}
