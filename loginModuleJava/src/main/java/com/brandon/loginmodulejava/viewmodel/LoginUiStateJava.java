package com.brandon.loginmodulejava.viewmodel;


import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;

import com.brandon.corejava.utilities.ConnectionErrors;
import com.brandon.corejava.utilities.UserInputErrors;

import java.util.Objects;


public final class LoginUiStateJava {
    private String email;
    private final UserInputErrors isEmailError;
    private final ConnectionErrors isGenericError;
    private final boolean isCodeSent;
    private final LoginUiStates state;


    public LoginUiStateJava(String email, UserInputErrors isEmailError, ConnectionErrors isGenericError, boolean isCodeSent, LoginUiStates state) {
        this.email = email;
        this.isEmailError = isEmailError;
        this.isGenericError = isGenericError;
        this.isCodeSent = isCodeSent;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGenericError() {
        return isGenericError != NO_CONNECTION_ERROR;
    }

    public boolean isEmailError() {
        return isEmailError != NO_INPUT_ERROR;
    }

    public boolean isCodeSent() {
        return isCodeSent;
    }

    public LoginUiStates getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginUiStateJava)) return false;
        LoginUiStateJava that = (LoginUiStateJava) o;
        return isCodeSent == that.isCodeSent && Objects.equals(email, that.email) && isEmailError == that.isEmailError && isGenericError == that.isGenericError && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, isEmailError, isGenericError, isCodeSent, state);
    }
}

