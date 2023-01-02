package com.example.forzenbook.network

import retrofit2.Response

class ForzenApiServiceImpl: ForzenApiService {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(request: CreateAccountRequest): Response<Any> {
        TODO("Not yet implemented")
    }

}