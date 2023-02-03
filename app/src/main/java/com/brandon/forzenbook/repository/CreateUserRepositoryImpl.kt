package com.brandon.forzenbook.repository

import android.util.Log
import com.brandon.forzenbook.network.toCreateAccountRequest
import com.brandon.network.ForzenApiService

class CreateUserRepositoryImpl(
    private val forzenApiService: ForzenApiService,
) : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        val data = createUserData.toCreateAccountRequest()
        try {
            val response = forzenApiService.createUser(
                firstname = data.firstName,
                lastname = data.lastName,
                email = data.email,
                location = data.location,
                dateOfBirth = data.date,
            )
            Log.e(DATA_ERROR_TAG, "$response")
            Log.e(DATA_ERROR_TAG, "${response.code()}")
            if (response.isSuccessful) {
                return
            } else if (response.code() == USER_DUPLICATE_CODE) {
                Log.e(
                    DATA_ERROR_TAG,
                    USER_DUPLICATE_ERROR_LOG_MESSAGE
                )
                throw UserAlreadyExistsException(USER_DUPLICATE_ERROR_LOG_MESSAGE)
            } else throw Exception(ACCOUNT_CREATION_FAILED)
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
    }

    companion object {
        const val DATA_ERROR_TAG = "Brandon_Test_Data"
        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val ACCOUNT_CREATION_FAILED = "Failed to Create Account."
        const val USER_DUPLICATE_CODE = 409
        const val USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists."
    }
}