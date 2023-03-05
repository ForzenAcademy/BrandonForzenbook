package com.brandon.loginmodulejava.viewmodel;



import static com.brandon.corejava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.CODE_SENT;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.GET_CODE;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.LOGIN_SUCCESS;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.IDLE;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.LOADING;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.LOGGED_IN;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.ViewModel;


import com.brandon.loginmodulejava.domain.LoginUseCaseJava;

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

    @SuppressLint("CheckResult")
    public void loginClicked(String email, @Nullable String code) {
        Single.fromCallable(() -> {
                    setState(new LoginUiStateJava(
                            email,
                            NO_INPUT_ERROR,
                            NO_CONNECTION_ERROR,
                            true,
                            LOADING,
                            false)
                    );
                    return loginUseCaseJava().invoke(email, code);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(result -> {
                    if (result.getState() == LOGIN_SUCCESS) {
                        setState(new LoginUiStateJava(
                                email,
                                result.getEmailError(),
                                result.getConnectionError(),
                                true,
                                LOGGED_IN,
                                false)
                        );
                    } else if (result.getState() == CODE_SENT) {
                        setState(new LoginUiStateJava(
                                email,
                                result.getEmailError(),
                                result.getConnectionError(),
                                true,
                                IDLE,
                                false)
                        );
                    } else if (result.getState() == GET_CODE) {
                        setState(new LoginUiStateJava(
                                email,
                                result.getEmailError(),
                                result.getConnectionError(),
                                false,
                                IDLE,
                                false)
                        );
                    }
                })
                .doOnError(e -> {
                    Log.e(VIEWMODEL_ERROR_TAG, e.toString());
                    setState(new LoginUiStateJava(
                            email,
                            NO_INPUT_ERROR,
                            CONNECTION_ERROR,
                            false,
                            IDLE,
                            false)
                    );
                });
    }
}
