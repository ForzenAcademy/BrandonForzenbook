package com.brandon.forzenbook.repository.mocks

import android.util.Log
import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.mocks.ForzenRepositoryAlwaysReturnsMock.Companion.REPOSITORY_MOCK_TAG

class ForzenRepositoryAlwaysReturnsMock() : ForzenRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    companion object {
        const val REPOSITORY_MOCK_TAG = "Brandon_Test_Data"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Data Mock."
    }
}

class ForzenRepositoryNeverReturnsMock() : ForzenRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class ForzenRepositoryAlwaysThrowsMock() : ForzenRepository {
    override suspend fun getCode(email: String) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun getToken(email: String, code: String) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}