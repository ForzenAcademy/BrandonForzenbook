package com.brandon.createaccountjava.viewmodel;


import static com.brandon.corejava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.corejava.utilities.UserInputErrors.NO_INPUT_ERROR;
import static com.brandon.createaccountjava.viewmodel.CreateAccountUiStates.IDLE;

import com.brandon.corejava.utilities.ConnectionErrors;
import com.brandon.corejava.utilities.UserInputErrors;

import java.util.Objects;

public class CreateAccountUiStateJava {
    private String firstName;
    private String lastName;
    private String email;
    private String date;
    private String location;
    private final UserInputErrors firstNameError;
    private final UserInputErrors lastNameError;
    private final UserInputErrors emailError;
    private final UserInputErrors dateError;
    private final UserInputErrors locationError;
    private final ConnectionErrors connectionError;
    private final CreateAccountUiStates state;
    private boolean isDateDialogOpen;
    private boolean isConnectionDialogOpen;
    private final boolean isSuccess;

    public CreateAccountUiStateJava(
            CreateAccountUiStates state,
            ConnectionErrors connectionError,
            String firstName, String lastName,
            String email, String date,
            String location,
            UserInputErrors firstNameError,
            UserInputErrors lastNameError,
            UserInputErrors emailError,
            UserInputErrors dateError,
            UserInputErrors locationError,
            boolean isDateDialogOpen,
            boolean isConnectionDialogOpen,
            boolean isSuccess) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.date = date;
        this.location = location;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.dateError = dateError;
        this.locationError = locationError;
        this.connectionError = connectionError;
        this.state = state;
        this.isDateDialogOpen = isDateDialogOpen;
        this.isConnectionDialogOpen = isConnectionDialogOpen;
        this.isSuccess = isSuccess;
    }

    public CreateAccountUiStateJava() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.date = "";
        this.location = "";
        this.firstNameError = NO_INPUT_ERROR;
        this.lastNameError = NO_INPUT_ERROR;
        this.emailError = NO_INPUT_ERROR;
        this.dateError = NO_INPUT_ERROR;
        this.locationError = NO_INPUT_ERROR;
        this.connectionError = NO_CONNECTION_ERROR;
        this.state = IDLE;
        this.isDateDialogOpen = false;
        this.isConnectionDialogOpen = false;
        this.isSuccess = false;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public boolean getFirstNameError() {
        return firstNameError != NO_INPUT_ERROR;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public boolean getLastNameError() {
        return lastNameError != NO_INPUT_ERROR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEmailError() {
        return emailError != NO_INPUT_ERROR;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getDateError() {
        return dateError != NO_INPUT_ERROR;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getLocationError() {
        return locationError != NO_INPUT_ERROR;
    }

    public boolean getConnectionError() {
        return connectionError != NO_CONNECTION_ERROR;
    }

    public CreateAccountUiStates getState() {
        return state;
    }

    public boolean isDateDialogOpen() {
        return isDateDialogOpen;
    }

    public void setDateDialogOpen(boolean isOpen) {
        isDateDialogOpen = isOpen;
    }

    public boolean isConnectionDialogOpen() {
        return isConnectionDialogOpen;
    }

    public void setConnectionDialogOpen(boolean isOpen) {
        this.isConnectionDialogOpen = isOpen;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAccountUiStateJava)) return false;
        CreateAccountUiStateJava that = (CreateAccountUiStateJava) o;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email)
                && Objects.equals(date, that.date)
                && Objects.equals(location, that.location)
                && firstNameError == that.firstNameError
                && lastNameError == that.lastNameError
                && emailError == that.emailError
                && dateError == that.dateError
                && locationError == that.locationError
                && connectionError == that.connectionError
                && state == that.state
                && isDateDialogOpen == that.isDateDialogOpen
                && isConnectionDialogOpen == that.isDateDialogOpen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                firstName,
                lastName,
                email,
                date,
                location,
                firstNameError,
                lastNameError,
                emailError,
                dateError,
                locationError,
                connectionError,
                state,
                isDateDialogOpen,
                isConnectionDialogOpen
        );
    }
}