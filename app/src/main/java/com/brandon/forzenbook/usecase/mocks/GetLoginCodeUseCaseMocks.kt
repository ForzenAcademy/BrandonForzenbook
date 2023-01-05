package com.brandon.forzenbook.usecase.mocks

import android.util.Log
import com.brandon.forzenbook.usecase.GetLoginCodeUseCase
import com.brandon.forzenbook.usecase.mocks.GetLoginCodeUseCaseAlwaysSuccessMock.Companion.USECASE_MOCK_TAG

class GetLoginCodeUseCaseAlwaysSuccessMock : GetLoginCodeUseCase {
    override suspend fun invoke(email: String): Boolean {
        Log.e(USECASE_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return true
    }

    companion object {
        const val USECASE_MOCK_TAG = "Brandon_Test_UseCase"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Usecase Mock."
    }
}

class GetLoginCodeUseCaseAlwaysFailsMock : GetLoginCodeUseCase {
    override suspend fun invoke(email: String): Boolean {
        Log.e(USECASE_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return false
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Usecase Mock."
    }
}

class GetLoginCodeUseCaseAlwaysThrowsMock : GetLoginCodeUseCase {
    override suspend fun invoke(email: String): Boolean {
        Log.e(USECASE_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Usecase Mock."
    }
}