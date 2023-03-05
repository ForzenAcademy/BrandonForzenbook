package com.brandon.loginmodulejava.viewmodel;


import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.loginmodulejava.viewmodel.LoginUiStates.IDLE;

import com.brandon.corejava.utilities.ConnectionErrors;
import com.brandon.corejava.utilities.UserInputErrors;

import java.util.Objects;


public final class LoginUiStateJava {
    private String email;
    private final UserInputErrors isEmailError;
    private final ConnectionErrors isGenericError;
    private final boolean isCodeSent;
    private final LoginUiStates state;
    private boolean isConnectionDialogOpen;


    public LoginUiStateJava(
            String email,
            UserInputErrors isEmailError,
            ConnectionErrors isGenericError,
            boolean isCodeSent,
            LoginUiStates state,
            boolean isConnectionDialogOpen
    ) {
        this.email = email;
        this.isEmailError = isEmailError;
        this.isGenericError = isGenericError;
        this.isCodeSent = isCodeSent;
        this.state = state;
        this.isConnectionDialogOpen = isConnectionDialogOpen;
    }

    public LoginUiStateJava() {
        this.email = "";
        this.isEmailError = NO_INPUT_ERROR;
        this.isGenericError = NO_CONNECTION_ERROR;
        this.isCodeSent = false;
        this.state = IDLE;
        this.isConnectionDialogOpen = false;
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

    public boolean isConnectionDialogOpen() {
        return isConnectionDialogOpen;
    }

    public void setConnectionDialogOpen(boolean isOpen) {
        this.isConnectionDialogOpen = isOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginUiStateJava)) return false;
        LoginUiStateJava that = (LoginUiStateJava) o;
        return isCodeSent == that.isCodeSent
                && Objects.equals(email, that.email)
                && isEmailError == that.isEmailError
                && isGenericError == that.isGenericError
                && state == that.state
                && isConnectionDialogOpen == that.isConnectionDialogOpen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, isEmailError, isGenericError, isCodeSent, state, isConnectionDialogOpen);
    }
}

