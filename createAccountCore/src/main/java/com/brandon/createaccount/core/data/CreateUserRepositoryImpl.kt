package com.brandon.createaccount.core.data

import android.util.Log
import com.brandon.network.ForzenApiService
import com.brandon.network.createuser.CreateUserResponse
import com.google.gson.Gson
import retrofit2.Response

class CreateUserRepositoryImpl(
    private val forzenApiService: ForzenApiService,
) : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        val data = createUserData.toCreateAccountRequest()
        val response: Response<CreateUserResponse>?
        try {
            response = forzenApiService.createUser(
                firstname = data.firstName,
                lastname = data.lastName,
                email = data.email,
                location = data.location,
                dateOfBirth = data.date,
            )
            Log.e(DATA_ERROR_TAG, "$response")
            Log.e(DATA_ERROR_TAG, "${response.code()}")
        } catch (e: Exception) {
            Log.e(DATA_ERROR_TAG, "$API_CALL_FAILED $e")
            throw Exception(API_CALL_FAILED)
        }
        if (response.isSuccessful) return
        else if (response.code() == USER_DUPLICATE_CODE) {
            Log.e(DATA_ERROR_TAG, USER_DUPLICATE_ERROR_LOG_MESSAGE)
            throw UserAlreadyExistsException(USER_DUPLICATE_ERROR_LOG_MESSAGE)
        } else {
            try {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), CreateUserResponse::class.java)
                Log.e(DATA_ERROR_TAG, "${error?.reason}")
                when (error?.reason) {
                    INVALID_FIRST_NAME -> throw InvalidFirstNameException()
                    INVALID_LAST_NAME -> throw InvalidLastNameException()
                    INVALID_EMAIL -> throw InvalidEmailException()
                    INVALID_LOCATION -> throw InvalidLocationException()
                    INVALID_DATE_OF_BIRTH -> throw InvalidBirthDateException()
                    else -> throw Exception(RESPONSE_CODE_ERROR)
                }
            } catch (e: Exception) {
                throw Exception(FAILED_TO_CONVERT_ERROR)
            }

        }
    }

    companion object {
        const val DATA_ERROR_TAG = "Brandon_Test_Data"
        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val RESPONSE_CODE_ERROR = "Unexpected Response Code."
        const val FAILED_TO_CONVERT_ERROR = "Failed to Convert Response Error."
        const val USER_DUPLICATE_CODE = 409
        const val USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists."
        const val INVALID_FIRST_NAME = "invalid first name"
        const val INVALID_LAST_NAME = "invalid last name"
        const val INVALID_EMAIL = "invalid email"
        const val INVALID_LOCATION = "location too long"
        const val INVALID_DATE_OF_BIRTH = "invalid birth date format"
    }
}