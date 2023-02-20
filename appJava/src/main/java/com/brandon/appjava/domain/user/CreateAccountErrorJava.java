package com.brandon.appjava.domain.user;

import com.brandon.appjava.utilities.ConnectionErrors;
import com.brandon.appjava.utilities.UserInputErrors;

import java.util.Objects;


public class CreateAccountErrorJava {
    private final UserInputErrors firstNameError;
    private final UserInputErrors lastNameError;
    private final UserInputErrors emailError;
    private final UserInputErrors locationError;
    private final UserInputErrors dateOfBirthError;
    private final ConnectionErrors connectionErrors;
    private final CreateAccountValidationStates state;


    public CreateAccountErrorJava(UserInputErrors firstNameError, UserInputErrors lastNameError, UserInputErrors emailError, UserInputErrors locationError, UserInputErrors dateOfBirthError, ConnectionErrors connectionErrors, CreateAccountValidationStates state) {
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.locationError = locationError;
        this.dateOfBirthError = dateOfBirthError;
        this.connectionErrors = connectionErrors;
        this.state = state;
    }

    public UserInputErrors getFirstNameError() {
        return firstNameError;
    }

    public UserInputErrors getLastNameError() {
        return lastNameError;
    }

    public UserInputErrors getEmailError() {
        return emailError;
    }

    public UserInputErrors getLocationError() {
        return locationError;
    }

    public UserInputErrors getDateOfBirthError() {
        return dateOfBirthError;
    }

    public ConnectionErrors getConnectionErrors() {
        return connectionErrors;
    }

    public CreateAccountValidationStates getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAccountErrorJava)) return false;
        CreateAccountErrorJava that = (CreateAccountErrorJava) o;
        return firstNameError == that.firstNameError && lastNameError == that.lastNameError && emailError == that.emailError && locationError == that.locationError && dateOfBirthError == that.dateOfBirthError && connectionErrors == that.connectionErrors && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNameError, lastNameError, emailError, locationError, dateOfBirthError, connectionErrors, state);
    }
}

