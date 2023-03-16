package com.brandon.loginmodulejava.domain;


import static com.brandon.corejava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.INVALID_INPUT_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.CODE_SENT;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.GET_CODE;
import static com.brandon.loginmodulejava.domain.LoginValidationStates.LOGIN_SUCCESS;

import androidx.annotation.Nullable;

import com.brandon.corejava.utilities.AccountValidationJava;
import com.brandon.corejava.utilities.UserInputErrors;
import com.brandon.loginmodulejava.data.LoginRepoJava;

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
            final UserInputErrors validCode = isValidCode(code);
            if (validEmail == NO_INPUT_ERROR && validCode == NO_INPUT_ERROR) {
                try {
                    loginRepoJava.getToken(email, code);
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