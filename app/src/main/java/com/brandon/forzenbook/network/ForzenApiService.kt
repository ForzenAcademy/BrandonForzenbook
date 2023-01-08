package com.brandon.forzenbook.network

import retrofit2.Response
import retrofit2.http.*
import java.sql.Date

interface ForzenApiService {

    @POST(LOGIN_ENDPOINT)
    suspend fun login(
        @Body request: LoginRequest
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
    }
}