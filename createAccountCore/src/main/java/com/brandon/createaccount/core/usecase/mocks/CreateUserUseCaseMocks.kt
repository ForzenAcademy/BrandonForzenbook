package com.brandon.createaccount.core.usecase.mocks

import android.util.Log
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.utilities.CreateAccountValidationState
import com.brandon.createaccount.core.usecase.mocks.CreateUserUseCaseAlwaysSuccess.Companion.USECASE_MOCK_TAG

class CreateUserUseCaseAlwaysSuccess() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: String,
        location: String
    ): CreateAccountValidationState {
        Log.e(USECASE_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return CreateAccountValidationState.Success
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
        date: String,
        location: String
    ): CreateAccountValidationState {
        Log.e(USECASE_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return CreateAccountValidationState.CreateAccountError(true, true, true, true)
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
        date: String,
        location: String
    ): CreateAccountValidationState {
        Log.e(USECASE_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Usecase Mock."
    }
}