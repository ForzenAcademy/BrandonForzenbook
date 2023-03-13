package com.brandon.corejava.network;



import com.brandon.corejava.network.login.LoginResponseJava;
import com.brandon.corejava.network.user.CreateUserResponseJava;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ForzenApiServiceJava {
    String LOGIN_ENDPOINT = "login/";
    String CREATE_USER_ENDPOINT = "user/";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String BIRTH_DATE = "birth_date";
    String LOCATION = "location";
    String EMAIL = "email";
    String CODE = "code";

    @GET(LOGIN_ENDPOINT)
    Call<Void> requestEmailCode(@Query(EMAIL) String email);

    @FormUrlEncoded
    @POST(LOGIN_ENDPOINT)
    Call<LoginResponseJava> loginWithEmailCode(
            @Field(EMAIL) String email,
            @Field(CODE) String code
    );

    @FormUrlEncoded
    @POST(CREATE_USER_ENDPOINT)
    Call<CreateUserResponseJava> createUser(
            @Field(FIRST_NAME) String firstName,
            @Field(LAST_NAME) String lastName,
            @Field(EMAIL) String email,
            @Field(BIRTH_DATE) Date dateOfBirth,
            @Field(LOCATION) String location
    );

}
