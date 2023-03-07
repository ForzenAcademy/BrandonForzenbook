package com.brandon.corejava.network;



import com.brandon.corejava.network.login.LoginResponseJava;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ForzenApiServiceJava {
    String LOGIN_ENDPOINT = "login/";
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

}
