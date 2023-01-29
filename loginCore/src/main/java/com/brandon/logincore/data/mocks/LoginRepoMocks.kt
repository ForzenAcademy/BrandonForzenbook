package com.brandon.logincore.data.mocks

import android.util.Log
import com.brandon.logincore.data.mocks.LoginRepositoryAlwaysReturnsMock.Companion.REPOSITORY_MOCK_TAG

class LoginRepositoryAlwaysReturnsMock() : com.brandon.logincore.data.LoginRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    companion object {
        const val REPOSITORY_MOCK_TAG = "Brandon_Test_Data"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Data Mock."
    }
}

class LoginRepositoryNeverReturnsMock() : com.brandon.logincore.data.LoginRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class LoginRepositoryAlwaysThrowsMock() : com.brandon.logincore.data.LoginRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}