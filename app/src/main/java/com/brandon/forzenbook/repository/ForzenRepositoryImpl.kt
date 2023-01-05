package com.brandon.forzenbook.repository

import android.util.Log
import com.brandon.forzenbook.data.ForzenDao
import com.brandon.forzenbook.data.ForzenEntity
import com.brandon.forzenbook.data.toForzenEntity
import com.brandon.forzenbook.network.ForzenApiService
import com.brandon.forzenbook.network.LoginRequest
import com.brandon.forzenbook.network.toCreateAccountRequest

class ForzenRepositoryImpl(
    private val forzenApiService: ForzenApiService,
    private val forzenDao: ForzenDao,
) : ForzenRepository {

    override suspend fun getToken(userName: String, code: String): LoginToken? {
        var entityData: ForzenEntity?
        var token: LoginToken
        try {
            entityData = forzenDao.getLoginToken()
            if (entityData != null) {
                entityData.token?.let {
                    token = LoginToken(it)
                    return token
                }
            } else {
                forzenDao.deleteLoginToken()
                val loginRequest = LoginRequest(userName, code)
                try {
                    val response = forzenApiService.login(loginRequest)
                    if (response.isSuccessful) {
                        try {
                            entityData = response.body()?.toForzenEntity()
                            if (entityData != null) {
                                forzenDao.insert(entityData)
                                token = entityData.toLoginToken()
                                return token
                            }
                        } catch (e: Exception) {
                            Log.e(DATA_ERROR_TAG, "$FAILED_ENTITY_CONVERT ${response.body()}")
                            return null
                        }
                    } else {
                        Log.e(DATA_ERROR_TAG, "$RESPONSE_CODE_ERROR ${response.code()}")
                        return null
                    }
                } catch (e: Exception) {
                    Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
                    return null
                }
            }
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$DATABASE_ERROR_ACCESS $e")
            return null
        }
        Log.e(DATA_ERROR_TAG, END_OF_BRANCH)
        return null
    }

    override suspend fun createUser(createUserData: CreateUserData): Boolean {
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
                return true
            }
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            return false
        }
        Log.e(DATA_ERROR_TAG, END_OF_BRANCH)
        return false
    }

    companion object {
        const val DATA_ERROR_TAG = "Brandon_Test_Data"
        const val END_OF_BRANCH = "End of Branch and Failed."
        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val DATABASE_ERROR_ACCESS = "Error Accessing Database."
        const val FAILED_ENTITY_CONVERT = "Could Not Convert Response to Entity."
        const val RESPONSE_CODE_ERROR = "Unexpected Response Code."
    }
}