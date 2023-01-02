package com.example.forzenbook.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ForzenApiService {

    @POST(LOGIN_ENDPOINT)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST(CREATE_USER_ENDPOINT)
    suspend fun createUser(
        @Body request: CreateAccountRequest
    ): Response<Any>

    companion object {
        const val FORZEN_BASE_URL = "https://forzen.dev/api/"
        const val LOGIN_ENDPOINT = "login"
        const val CREATE_USER_ENDPOINT = "createUser"
    }
}