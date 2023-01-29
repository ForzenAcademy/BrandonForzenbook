package com.brandon.network

import android.util.Log
import com.brandon.network.login.LoginResponse
import com.brandon.network.ForzenApiServiceAlwaysSuccessMock.Companion.NETWORK_MOCK_TAG
import okhttp3.ResponseBody
import retrofit2.Response
import java.sql.Date

class ForzenApiServiceAlwaysSuccessMock : ForzenApiService {
    override suspend fun requestEmailCode(email: String): Response<Unit> {
        Log.v(NETWORK_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return Response.success(null)
    }

    override suspend fun loginWithEmailCode(email: String, code: String): Response<LoginResponse> {
        Log.v(NETWORK_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return Response.success(LoginResponse(token = SUCCESS_MOCK_TOKEN))
    }

    override suspend fun createUser(
        firstname: String,
        lastname: String,
        dateOfBirth: Date,
        location: String,
        email: String
    ): Response<Void> {
        Log.v(NETWORK_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return Response.success(null)
    }

    companion object {
        const val NETWORK_MOCK_TAG = "Brandon_Test_Network"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Network Mock."
        const val SUCCESS_MOCK_TOKEN =
            "ThisIsASixtyFour(64)CharacterLongStringForTestingNetworkResponse"
    }
}

class ForzenApiServiceAlwaysFailsMock : ForzenApiService {
    override suspend fun requestEmailCode(email: String): Response<Unit> {
        Log.v(NETWORK_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return Response.error(404, ResponseBody.create(null, FAILURE_MOCK_MESSAGE))
    }

    override suspend fun loginWithEmailCode(email: String, code: String): Response<LoginResponse> {
        Log.v(NETWORK_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return Response.error(404, ResponseBody.create(null, FAILURE_MOCK_MESSAGE))
    }

    override suspend fun createUser(
        firstname: String,
        lastname: String,
        dateOfBirth: Date,
        location: String,
        email: String
    ): Response<Void> {
        Log.v(NETWORK_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return Response.error(404, ResponseBody.create(null, FAILURE_MOCK_MESSAGE))
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class ForzenApiServiceAlwaysThrowsMock : ForzenApiService {
    override suspend fun requestEmailCode(email: String): Response<Unit> {
        Log.v(NETWORK_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun loginWithEmailCode(email: String, code: String): Response<LoginResponse> {
        Log.v(NETWORK_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun createUser(
        firstname: String,
        lastname: String,
        dateOfBirth: Date,
        location: String,
        email: String
    ): Response<Void> {
        Log.v(NETWORK_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}

class ForzenApiServiceDuplicateUserMock : ForzenApiService {
    override suspend fun requestEmailCode(email: String): Response<Unit> {
        Log.v(NETWORK_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun loginWithEmailCode(email: String, code: String): Response<LoginResponse> {
        Log.v(NETWORK_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun createUser(
        firstname: String,
        lastname: String,
        dateOfBirth: Date,
        location: String,
        email: String
    ): Response<Void> {
        Log.v(NETWORK_MOCK_TAG, USER_DUPLICATE_ERROR_LOG_MESSAGE)
        return Response.error(USER_DUPLICATE_CODE, ResponseBody.create(null, ""))
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
        const val USER_DUPLICATE_CODE = 409
        const val USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists."
    }
}