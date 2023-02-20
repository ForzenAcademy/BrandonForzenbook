package com.brandon.appjava.domain.login;

import static com.brandon.appjava.domain.login.LoginValidationStates.CODE_SENT;
import static com.brandon.appjava.domain.login.LoginValidationStates.GET_CODE;
import static com.brandon.appjava.domain.login.LoginValidationStates.LOGIN_SUCCESS;
import static com.brandon.appjava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.INVALID_INPUT_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;

import android.util.Log;

import androidx.annotation.Nullable;

import com.brandon.appjava.data.login.LoginRepoJava;
import com.brandon.appjava.utilities.AccountValidationJava;
import com.brandon.appjava.utilities.UserInputErrors;

import javax.inject.Inject;

public class LoginUseCaseImplJava extends AccountValidationJava implements LoginUseCaseJava {
    private final LoginRepoJava loginRepoJava;

    @Inject
    public LoginUseCaseImplJava(LoginRepoJava loginRepoJava) {
        this.loginRepoJava = loginRepoJava;
    }

    @Override
    public LoginValidationStateJava invoke(String email, @Nullable String code) {
        final UserInputErrors validEmail = isValidEmail(email);
        if (code == null) {
            if (validEmail == NO_INPUT_ERROR) {
                try {
                    loginRepoJava.getCode(email);
                    return new LoginValidationStateJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, CODE_SENT);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new LoginValidationStateJava(NO_INPUT_ERROR, NO_INPUT_ERROR, CONNECTION_ERROR, GET_CODE);
                }
            } else {
                return new LoginValidationStateJava(INVALID_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, GET_CODE);
            }
        } else {
            Log.e("Brandon_Test", "Getting Token");
            final UserInputErrors validCode = isValidCode(code);
            if (validEmail == NO_INPUT_ERROR && validCode == NO_INPUT_ERROR) {
                try {
                    loginRepoJava.getToken(email, code);
                    Log.e("Brandon_Test", "Got Token");
                    return new LoginValidationStateJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, LOGIN_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new LoginValidationStateJava(NO_INPUT_ERROR, NO_INPUT_ERROR, CONNECTION_ERROR, CODE_SENT);
                }
            } else {
                return new LoginValidationStateJava(validEmail, validCode, NO_CONNECTION_ERROR, CODE_SENT);
            }
        }
    }
}