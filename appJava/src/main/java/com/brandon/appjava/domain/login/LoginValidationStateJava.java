package com.brandon.appjava.domain.login;

import com.brandon.appjava.utilities.ConnectionErrors;
import com.brandon.appjava.utilities.UserInputErrors;

import java.util.Objects;

public class LoginValidationStateJava {
    private final UserInputErrors emailError;
    private final UserInputErrors codeError;
    private final ConnectionErrors connectionError;
    private final LoginValidationStates state;

    public LoginValidationStateJava(UserInputErrors emailError, UserInputErrors codeError, ConnectionErrors genericError, LoginValidationStates state) {
        this.emailError = emailError;
        this.codeError = codeError;
        this.connectionError = genericError;
        this.state = state;
    }

    public UserInputErrors getEmailError() {
        return emailError;
    }

    public UserInputErrors getCodeError() {
        return codeError;
    }

    public ConnectionErrors getConnectionError() {
        return connectionError;
    }

    public LoginValidationStates getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginValidationStateJava)) return false;
        LoginValidationStateJava that = (LoginValidationStateJava) o;
        return connectionError == that.connectionError && emailError == that.emailError && codeError == that.codeError && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailError, codeError, connectionError, state);
    }
}
