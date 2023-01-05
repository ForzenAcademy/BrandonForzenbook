package com.brandon.forzenbook.network.mocks

import com.brandon.forzenbook.network.CreateAccountRequest
import com.brandon.forzenbook.network.ForzenApiService
import com.brandon.forzenbook.network.LoginRequest
import com.brandon.forzenbook.network.LoginResponse
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
        return Response.error(404, ResponseBody.create(null, "Not Found."))
    }

    override suspend fun createUser(request: CreateAccountRequest): Response<Any> {
        return Response.error(404, ResponseBody.create(null, "Not Found."))
    }

}

class ForzenApiServiceAlwaysThrowsMock : ForzenApiService {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        println("Brandon_Test_Network Always Throw Mock")
        throw Exception("Brandon_Test_Network Always Throw Mock")
    }

    override suspend fun createUser(request: CreateAccountRequest): Response<Any> {
        println("Brandon_Test_Network Always Throw Mock")
        throw Exception("Brandon_Test_Network Always Throw Mock")
    }

}