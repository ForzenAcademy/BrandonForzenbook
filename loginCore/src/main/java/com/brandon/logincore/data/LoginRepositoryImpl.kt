package com.brandon.logincore.data

import android.util.Log
import com.brandon.data.ForzenDao
import com.brandon.data.toForzenEntity
import com.brandon.network.ForzenApiService
import com.brandon.network.login.LoginResponse
import retrofit2.Response

class LoginRepositoryImpl(
    private val forzenApiService: ForzenApiService,
    private val forzenDao: ForzenDao,
) : LoginRepository {

    override suspend fun getCode(email: String) {
        val response: Response<Unit>
        try {
            response = forzenApiService.requestEmailCode(email)
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
        if (response.isSuccessful) return
        else {
            Log.e(DATA_ERROR_TAG, "$RESPONSE_CODE_ERROR $response")
            throw Exception(RESPONSE_CODE_ERROR)
        }
    }

    override suspend fun getToken(email: String, code: String) {
        val response: Response<LoginResponse>
        try {
            forzenDao.deleteLoginToken()
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$DATABASE_ERROR_ACCESS $e")
            throw Exception(DATABASE_ERROR_ACCESS)
        }
        try {
            response = forzenApiService.loginWithEmailCode(email, code)
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
        if (response.isSuccessful) {
            try {
                val entityData = response.body()?.toForzenEntity()
                if (entityData != null) {
                    forzenDao.insert(entityData)
                    return
                }
            } catch (e: Exception) {
                Log.e(DATA_ERROR_TAG, "$FAILED_ENTITY_CONVERT $response")
                throw Exception(FAILED_ENTITY_CONVERT)
            }
        } else {
            Log.e(DATA_ERROR_TAG, "$RESPONSE_CODE_ERROR $response")
            throw Exception(RESPONSE_CODE_ERROR)
        }
    }

    companion object {
        const val DATA_ERROR_TAG = "Brandon_Test_Data"
        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val DATABASE_ERROR_ACCESS = "Error Accessing Database."
        const val FAILED_ENTITY_CONVERT = "Could Not Convert Response to Entity."
        const val RESPONSE_CODE_ERROR = "Unexpected Response Code."
    }
}