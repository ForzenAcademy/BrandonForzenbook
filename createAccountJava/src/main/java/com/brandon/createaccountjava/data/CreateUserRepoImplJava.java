package com.brandon.createaccountjava.data;

import android.util.Log;

import com.brandon.corejava.network.ForzenApiServiceJava;
import com.brandon.corejava.network.user.CreateUserResponseJava;
import com.brandon.createaccountjava.data.exceptions.DuplicateUserExceptionJava;
import com.brandon.createaccountjava.data.exceptions.InvalidDateExceptionJava;
import com.brandon.createaccountjava.data.exceptions.InvalidEmailExceptionJava;
import com.brandon.createaccountjava.data.exceptions.InvalidFirstNameExceptionJava;
import com.brandon.createaccountjava.data.exceptions.InvalidLastNameExceptionJava;
import com.brandon.createaccountjava.data.exceptions.InvalidLocationExceptionJava;
import com.google.gson.Gson;

import java.sql.Date;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Response;

public class CreateUserRepoImplJava implements CreateUserRepoJava {
    private static final String DATA_ERROR_TAG = "Brandon_Test_Data";
    private static final String API_CALL_FAILED = "Failed to Connect to API Service.";
    private static final String RESPONSE_CODE_ERROR = "Unexpected Response Code.";
    private static final String FAILED_TO_CONVERT_ERROR = "Failed to Convert Response Error.";
    private static final int USER_DUPLICATE_CODE = 409;
    private static final String USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists.";
    private static final String INVALID_FIRST_NAME = "invalid first name";
    private static final String INVALID_LAST_NAME = "invalid last name";
    private static final String INVALID_EMAIL = "invalid email";
    private static final String INVALID_LOCATION = "location too long";
    private static final String INVALID_DATE_OF_BIRTH = "invalid birth date format";

    private final ForzenApiServiceJava forzenApiService;

    @Inject
    public CreateUserRepoImplJava(ForzenApiServiceJava forzenApiService) {
        this.forzenApiService = forzenApiService;
    }


    @Override
    public void createUser(String firstName, String lastName, String email, Date dateOfBirth, String location) throws Exception {
        final Response<CreateUserResponseJava> response;
        try {
            response = forzenApiService.createUser(
                    firstName,
                    lastName,
                    email,
                    dateOfBirth,
                    location
            ).execute();
            if (response.code() == USER_DUPLICATE_CODE) {
                Log.e(DATA_ERROR_TAG, USER_DUPLICATE_ERROR_LOG_MESSAGE);
                throw new DuplicateUserExceptionJava(USER_DUPLICATE_ERROR_LOG_MESSAGE);
            } else if (!response.isSuccessful()) {
                try {
                    assert response.errorBody() != null;
                    final CreateUserResponseJava error =
                            new Gson().fromJson(response.errorBody().string(), CreateUserResponseJava.class);
                    if (Objects.equals(error.getReason(), INVALID_FIRST_NAME))
                        throw new InvalidFirstNameExceptionJava(INVALID_FIRST_NAME);
                    if (Objects.equals(error.getReason(), INVALID_LAST_NAME))
                        throw new InvalidLastNameExceptionJava(INVALID_LAST_NAME);
                    if (Objects.equals(error.getReason(), INVALID_EMAIL))
                        throw new InvalidEmailExceptionJava(INVALID_EMAIL);
                    if (Objects.equals(error.getReason(), INVALID_LOCATION))
                        throw new InvalidLocationExceptionJava(INVALID_LOCATION);
                    if (Objects.equals(error.getReason(), INVALID_DATE_OF_BIRTH))
                        throw new InvalidDateExceptionJava(INVALID_DATE_OF_BIRTH);
                    else throw new Exception(RESPONSE_CODE_ERROR);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(FAILED_TO_CONVERT_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(API_CALL_FAILED);
        }
    }
}
