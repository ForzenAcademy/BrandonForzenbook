package com.brandon.forzenbook.usecase.mocks

import android.util.Log
import com.brandon.forzenbook.usecase.CreateUserUseCase
import com.brandon.forzenbook.usecase.mocks.CreateUserUseCaseAlwaysSuccess.Companion.USECASE_MOCK_TAG
import com.brandon.forzenbook.viewmodels.CreateUserOutcome
import java.sql.Date

class CreateUserUseCaseAlwaysSuccess() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserOutcome {
        Log.e(USECASE_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return CreateUserOutcome.CREATE_USER_SUCCESS
    }

    companion object {
        const val USECASE_MOCK_TAG = "Brandon_Test_UseCase"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Usecase Mock."
    }
}

class CreateUserUseCaseNeverSuccess() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserOutcome {
        Log.e(USECASE_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return CreateUserOutcome.CREATE_USER_FAILURE
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Usecase Mock."
    }
}

class CreateUserUseCaseAlwaysThrows() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserOutcome {
        Log.e(USECASE_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Usecase Mock."
    }
}