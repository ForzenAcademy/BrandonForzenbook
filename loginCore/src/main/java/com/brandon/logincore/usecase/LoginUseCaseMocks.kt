package com.brandon.logincore.usecase

import android.util.Log
import com.brandon.utilities.LoginValidationState
import com.brandon.logincore.usecase.LoginUseCaseAlwaysReturns.Companion.USECASE_MOCK_TAG

class LoginUseCaseAlwaysReturns() : LoginUseCase {
    override suspend fun invoke(email: String, code: String?): LoginValidationState {
        Log.e(USECASE_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return LoginValidationState.Success
    }

    companion object {
        const val USECASE_MOCK_TAG = "Brandon_Test_Usecase"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Usecase Mock."
    }
}

class LoginUseCaseNeverReturns() : LoginUseCase {
    override suspend fun invoke(email: String, code: String?): LoginValidationState {
        Log.e(USECASE_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return LoginValidationState.LoginError(true, true, true)
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails UseCase Mock."
    }
}

class LoginUseCaseAlwaysThrows() : LoginUseCase {
    override suspend fun invoke(email: String, code: String?): LoginValidationState {
        Log.e(USECASE_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception UseCase Mock."
    }
}