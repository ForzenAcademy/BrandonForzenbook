package com.brandon.network

import com.brandon.network.login.LoginResponse
import retrofit2.Response
import retrofit2.http.*
import java.sql.Date

interface ForzenApiService {

    @GET(LOGIN_ENDPOINT)
    suspend fun requestEmailCode(
        @Query("email") email: String
    ): Response<Unit>

    @FormUrlEncoded
    @POST(LOGIN_ENDPOINT)
    suspend fun loginWithEmailCode(
        @Field(EMAIL) email: String,
        @Field(CODE) code: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST(CREATE_USER_ENDPOINT)
    suspend fun createUser(
        @Field(FIRST_NAME) firstname: String,
        @Field(LAST_NAME) lastname: String,
        @Field(BIRTH_DATE) dateOfBirth: Date,
        @Field(LOCATION) location: String,
        @Field(EMAIL) email: String,
    ): Response<Void>

    companion object {
        const val FORZEN_BASE_URL = "https://forzen.dev/api/"
        const val LOGIN_ENDPOINT = "login/"
        const val CREATE_USER_ENDPOINT = "user/"

        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val BIRTH_DATE = "birth_date"
        const val LOCATION = "location"
        const val EMAIL = "email"
        const val CODE = "code"
    }
}