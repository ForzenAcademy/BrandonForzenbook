package com.brandon.appjava.domain.user;

import static com.brandon.appjava.domain.user.CreateAccountValidationStates.USER_ALREADY_EXISTS;
import static com.brandon.appjava.domain.user.CreateAccountValidationStates.FAILURE;
import static com.brandon.appjava.domain.user.CreateAccountValidationStates.ACCOUNT_CREATED;
import static com.brandon.appjava.utilities.ConnectionErrors.CONNECTION_ERROR;
import static com.brandon.appjava.utilities.ConnectionErrors.NO_CONNECTION_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.INVALID_INPUT_ERROR;
import static com.brandon.appjava.utilities.UserInputErrors.NO_INPUT_ERROR;

import com.brandon.appjava.data.user.CreateUserRepoJava;
import com.brandon.appjava.data.user.exceptions.DuplicateUserExceptionJava;
import com.brandon.appjava.data.user.exceptions.InvalidDateExceptionJava;
import com.brandon.appjava.data.user.exceptions.InvalidEmailExceptionJava;
import com.brandon.appjava.data.user.exceptions.InvalidFirstNameExceptionJava;
import com.brandon.appjava.data.user.exceptions.InvalidLastNameExceptionJava;
import com.brandon.appjava.data.user.exceptions.InvalidLocationExceptionJava;
import com.brandon.appjava.utilities.AccountValidationJava;
import com.brandon.appjava.utilities.UserInputErrors;

import java.sql.Date;

import javax.inject.Inject;

public class CreateUserUseCaseImplJava extends AccountValidationJava implements CreateUserUseCaseJava {
    private final CreateUserRepoJava createUserRepoJava;

    @Inject
    public CreateUserUseCaseImplJava(CreateUserRepoJava createUserRepoJava) {
        this.createUserRepoJava = createUserRepoJava;
    }

    @Override
    public CreateAccountErrorJava invoke(String firstName, String lastName, String email, String dateOfBirth, String location) {
        final UserInputErrors validEmail = isValidEmail(email);
        final UserInputErrors validLocation = isValidString(location);
        final UserInputErrors validFirstName = isValidName(firstName);
        final UserInputErrors validLastName = isValidName(lastName);
        final Date validDate = isValidDate(dateOfBirth);
        if (validEmail == NO_INPUT_ERROR && validDate != null && validLocation == NO_INPUT_ERROR && validLastName == NO_INPUT_ERROR && validFirstName == NO_INPUT_ERROR) {
            try {
                createUserRepoJava.createUser(firstName, lastName, email, validDate, location);
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, ACCOUNT_CREATED);
            } catch (DuplicateUserExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, USER_ALREADY_EXISTS);
            } catch (InvalidFirstNameExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(INVALID_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } catch (InvalidLastNameExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, INVALID_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } catch (InvalidEmailExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, INVALID_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } catch (InvalidDateExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, INVALID_INPUT_ERROR, NO_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } catch (InvalidLocationExceptionJava e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, INVALID_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } catch (Exception e) {
                e.printStackTrace();
                return new CreateAccountErrorJava(NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, NO_INPUT_ERROR, CONNECTION_ERROR, FAILURE);
            }
        } else {
            if (validDate != null) {
                return new CreateAccountErrorJava(validFirstName, validLastName, validEmail, validLocation, NO_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            } else {
                return new CreateAccountErrorJava(validFirstName, validLastName, validEmail, validLocation, INVALID_INPUT_ERROR, NO_CONNECTION_ERROR, FAILURE);
            }
        }
    }
}
