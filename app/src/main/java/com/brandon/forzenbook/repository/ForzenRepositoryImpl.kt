package com.brandon.forzenbook.repository

import android.util.Log
import com.brandon.forzenbook.data.ForzenDao
import com.brandon.forzenbook.data.toForzenEntity
import com.brandon.forzenbook.network.ForzenApiService
import com.brandon.forzenbook.network.LoginRequest
import com.brandon.forzenbook.network.toCreateAccountRequest

class ForzenRepositoryImpl(
    private val forzenApiService: ForzenApiService,
    private val forzenDao: ForzenDao,
) : ForzenRepository {

    override suspend fun getCode(email: String) {
        try {
            val response = forzenApiService.getCode(email)
            if (response.isSuccessful) {
                return
            } else {
                Log.e(DATA_ERROR_TAG, "$RESPONSE_CODE_ERROR ${response.code()}")
                throw Exception(RESPONSE_CODE_ERROR)
            }
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
    }

    override suspend fun getToken(email: String, code: String) {
        try {
            forzenDao.deleteLoginToken()
            val loginRequest = LoginRequest(email, code)
            try {
                val response = forzenApiService.login(loginRequest)
                if (response.isSuccessful) {
                    try {
                        val entityData = response.body()?.toForzenEntity()
                        if (entityData != null) {
                            forzenDao.insert(entityData)
                            return
                        }
                    } catch (e: Exception) {
                        Log.e(DATA_ERROR_TAG, "$FAILED_ENTITY_CONVERT ${response.body()}")
                        throw Exception(FAILED_ENTITY_CONVERT)
                    }
                } else {
                    Log.e(DATA_ERROR_TAG, "$RESPONSE_CODE_ERROR ${response.code()}")
                    throw Exception(RESPONSE_CODE_ERROR)
                }
            } catch (e: Exception) {
                Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
                throw Exception(API_CALL_FAILED)
            }
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$DATABASE_ERROR_ACCESS $e")
            throw Exception(DATABASE_ERROR_ACCESS)
        }
        Log.e(DATA_ERROR_TAG, END_OF_BRANCH)
        throw Exception(END_OF_BRANCH)
    }

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
                Log.e(DATA_ERROR_TAG, USER_DUPLICATE_ERROR_LOG_MESSAGE)
                throw UserAlreadyExistsException(USER_DUPLICATE_ERROR_LOG_MESSAGE)
            } else throw Exception(ACCOUNT_CREATION_FAILED)
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
    }

    companion object {
        const val DATA_ERROR_TAG = "Brandon_Test_Data"
        const val END_OF_BRANCH = "End of Branch and Failed."
        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val DATABASE_ERROR_ACCESS = "Error Accessing Database."
        const val FAILED_ENTITY_CONVERT = "Could Not Convert Response to Entity."
        const val RESPONSE_CODE_ERROR = "Unexpected Response Code."
        const val ACCOUNT_CREATION_FAILED = "Failed to Create Account."
        const val USER_DUPLICATE_CODE = 409
        const val USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists."
    }
}