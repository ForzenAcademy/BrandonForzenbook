package com.brandon.createaccountjava.viewmodel;


import static com.brandon.corejava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.createaccountjava.domain.CreateAccountValidationStates.ACCOUNT_CREATED;
import static com.brandon.createaccountjava.domain.CreateAccountValidationStates.FAILURE;
import static com.brandon.createaccountjava.domain.CreateAccountValidationStates.USER_ALREADY_EXISTS;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.ERROR;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.IDLE;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.LOADING;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.brandon.createaccountjava.domain.CreateUserUseCaseJava;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class CreateAccountViewModel extends ViewModel {
    private final String VIEWMODEL_ERROR_TAG = "Brandon_Test ViewModel";

    protected abstract CreateUserUseCaseJava createUserUseCaseJava();

    protected abstract ConnectivityManager connectivityManager();

    public boolean checkInternetConnection() {
        return connectivityManager().getActiveNetworkInfo().isConnected();
    }

    protected abstract void setState(CreateAccountUiStateJava createAccountUiStateJava);

    @SuppressLint("CheckResult")
    public void createAccountClicked(
            String firstName,
            String lastName,
            String email,
            String date,
            String location
    ) {
        Single.fromCallable(() -> {
                    setState(new CreateAccountUiStateJava(
                            LOADING,
                            NO_CONNECTION_ERROR,
                            firstName,
                            lastName,
                            email,
                            date, location,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            false,
                            false,
                            false)
                    );
                    return createUserUseCaseJava().invoke(firstName, lastName, email, date, location);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(result -> {
                    if (result.getState() == ACCOUNT_CREATED) {
                        setState(new CreateAccountUiStateJava(
                                IDLE,
                                result.getConnectionErrors(),
                                firstName,
                                lastName,
                                email,
                                date,
                                location,
                                result.getFirstNameError(),
                                result.getLastNameError(),
                                result.getEmailError(),
                                result.getDateOfBirthError(),
                                result.getLocationError(),
                                false,
                                false,
                                true)
                        );
                    } else if (result.getState() == USER_ALREADY_EXISTS) {
                        setState(new CreateAccountUiStateJava(
                                        ERROR,
                                        result.getConnectionErrors(),
                                        firstName,
                                        lastName,
                                        email,
                                        date,
                                        location,
                                        result.getFirstNameError(),
                                        result.getLastNameError(),
                                        result.getEmailError(),
                                        result.getDateOfBirthError(),
                                        result.getLocationError(),
                                        false,
                                        false,
                                        false
                                )
                        );
                    } else if (result.getState() == FAILURE) {
                        setState(new CreateAccountUiStateJava(
                                IDLE,
                                result.getConnectionErrors(),
                                firstName,
                                lastName,
                                email,
                                date,
                                location,
                                result.getFirstNameError(),
                                result.getLastNameError(),
                                result.getEmailError(),
                                result.getDateOfBirthError(),
                                result.getLocationError(),
                                false,
                                false,
                                false)
                        );
                    }
                })
                .doOnError(e -> {
                    Log.e(VIEWMODEL_ERROR_TAG, e.toString());
                    setState(new CreateAccountUiStateJava(
                            IDLE,
                            CONNECTION_ERROR,
                            firstName,
                            lastName,
                            email,
                            date,
                            location,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            NO_INPUT_ERROR,
                            false,
                            false,
                            false)
                    );
                });
    }
}
