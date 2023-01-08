package com.brandon.forzenbook.repository.mocks

import android.util.Log
import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.LoginToken
import com.brandon.forzenbook.repository.mocks.ForzenRepositoryAlwaysReturnsMock.Companion.REPOSITORY_MOCK_TAG

class ForzenRepositoryAlwaysReturnsMock() : ForzenRepository {

    override suspend fun getToken(userName: String, code: String): LoginToken {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return LoginToken(token = SUCCESS_MOCK_TOKEN)
    }

    override suspend fun createUser(createUserData: CreateUserData): Boolean {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return true
    }

    companion object {
        const val REPOSITORY_MOCK_TAG = "Brandon_Test_Data"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Data Mock."
        const val SUCCESS_MOCK_TOKEN =
            "ThisIsASixtyFour(64)CharacterLongStringForTestingNetworkResponse"
    }
}

class ForzenRepositoryNeverReturnsMock() : ForzenRepository {

    override suspend fun getToken(userName: String, code: String): LoginToken {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return LoginToken(null)
    }

    override suspend fun createUser(createUserData: CreateUserData): Boolean {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        return false
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class ForzenRepositoryAlwaysThrowsMock() : ForzenRepository {

    override suspend fun getToken(userName: String, code: String): LoginToken? {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    override suspend fun createUser(createUserData: CreateUserData): Boolean {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }
    
    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}