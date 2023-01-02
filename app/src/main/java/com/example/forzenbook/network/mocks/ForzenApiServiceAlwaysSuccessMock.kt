package com.example.forzenbook.network.mocks

import com.example.forzenbook.network.CreateAccountRequest
import com.example.forzenbook.network.ForzenApiService
import com.example.forzenbook.network.LoginRequest
import com.example.forzenbook.network.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response

class ForzenApiServiceAlwaysSuccessMock : ForzenApiService {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        println("Brandon_Test_Network Always Success Mock")
        return Response.success(
            LoginResponse(
                token = "ThisIsASixtyFour(64)CharacterLongStringForTestingNetworkResponse"
            )
        )
    }

    override suspend fun createUser(request: CreateAccountRequest): Response<Any> {
        return Response.success(200)
    }

}

class ForzenApiServiceAlwaysFailsMock : ForzenApiService {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        println("Brandon_Test_Network Always Fail Mock")
        return Response.error(404, ResponseBody.create(null,"Not Found."))
    }

    override suspend fun createUser(request: CreateAccountRequest): Response<Any> {
        return Response.error(404, ResponseBody.create(null,"Not Found."))
    }

}