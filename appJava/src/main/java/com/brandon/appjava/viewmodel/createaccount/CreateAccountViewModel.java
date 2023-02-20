package com.brandon.appjava.viewmodel.createaccount;

import static com.brandon.appjava.domain.user.CreateAccountValidationStates.ACCOUNT_CREATED;
import static com.brandon.appjava.domain.user.CreateAccountValidationStates.FAILURE;
import static com.brandon.appjava.domain.user.CreateAccountValidationStates.USER_ALREADY_EXISTS;
import static com.brandon.appjava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.DUPLICATE;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.IDLE;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.LOADING;
import static com.brandon.appjava.viewmodel.createaccount.CreateAccountUiStates.SUCCESS;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.brandon.appjava.domain.user.CreateUserUseCaseJava;

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

    public void createAccountClicked(
            String firstName,
            String lastName,
            String email,
            String date,
            String location
    ) {
        Single.fromCallable(() -> {
                    setState(new CreateAccountUiStateJava(LOADING, NO_CONNECTION_ERROR, firstName, lastName, email, date, location, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR));
                    return createUserUseCaseJava().invoke(firstName, lastName, email, date, location);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(result -> {
                    if (result.getState() == ACCOUNT_CREATED) {
                        setState(new CreateAccountUiStateJava(SUCCESS, result.getConnectionErrors(), firstName, lastName, email, date, location, result.getFirstNameError(), result.getLastNameError(), result.getEmailError(), result.getDateOfBirthError(), result.getLocationError()));
                    } else if (result.getState() == USER_ALREADY_EXISTS) {
                        setState(new CreateAccountUiStateJava(DUPLICATE, result.getConnectionErrors(), firstName, lastName, email, date, location, result.getFirstNameError(), result.getLastNameError(), result.getEmailError(), result.getDateOfBirthError(), result.getLocationError()));
                    } else if (result.getState() == FAILURE) {
                        setState(new CreateAccountUiStateJava(IDLE, result.getConnectionErrors(), firstName, lastName, email, date, location, result.getFirstNameError(), result.getLastNameError(), result.getEmailError(), result.getDateOfBirthError(), result.getLocationError()));
                    }
                })
                .doOnError(e -> {
                    Log.e(VIEWMODEL_ERROR_TAG, e.toString());
                    setState(new CreateAccountUiStateJava(IDLE, CONNECTION_ERROR, firstName, lastName, email, date, location, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR));
                });
    }
}
