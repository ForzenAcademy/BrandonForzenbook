package com.brandon.appjava.viewmodel.login;

import static com.brandon.appjava.domain.login.LoginValidationStates.CODE_SENT;
import static com.brandon.appjava.domain.login.LoginValidationStates.GET_CODE;
import static com.brandon.appjava.domain.login.LoginValidationStates.LOGIN_SUCCESS;
import static com.brandon.appjava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.appjava.viewmodel.login.LoginUiStates.IDLE;
import static com.brandon.appjava.viewmodel.login.LoginUiStates.LOADING;
import static com.brandon.appjava.viewmodel.login.LoginUiStates.LOGGED_IN;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.brandon.appjava.domain.login.LoginUseCaseJava;

import javax.annotation.Nullable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public abstract class LoginViewModel extends ViewModel {
    private final String VIEWMODEL_ERROR_TAG = "Brandon_Test ViewModel";

    protected abstract LoginUseCaseJava loginUseCaseJava();

    protected abstract ConnectivityManager connectivityManager();

    public boolean checkInternetConnection() {
        return connectivityManager().getActiveNetworkInfo().isConnected();
    }

    protected abstract void setState(LoginUiStateJava newState);

    public void loginClicked(String email, @Nullable String code) {
        Single.fromCallable(() -> {
                    setState(new LoginUiStateJava(email, NO_INPUT_ERROR, NO_CONNECTION_ERROR, true, LOADING));
                    return loginUseCaseJava().invoke(email, code);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(result -> {
                    if (result.getState() == LOGIN_SUCCESS) {
                        setState(new LoginUiStateJava(email, result.getEmailError(), result.getConnectionError(), true, LOGGED_IN));
                    } else if (result.getState() == CODE_SENT) {
                        setState(new LoginUiStateJava(email, result.getEmailError(), result.getConnectionError(), true, IDLE));
                    } else if (result.getState() == GET_CODE) {
                        setState(new LoginUiStateJava(email, result.getEmailError(), result.getConnectionError(), false, IDLE));
                    }
                })
                .doOnError(e -> {
                    Log.e(VIEWMODEL_ERROR_TAG, e.toString());
                    setState(new LoginUiStateJava(email, NO_INPUT_ERROR, CONNECTION_ERROR, false, IDLE));
                });
    }
}
