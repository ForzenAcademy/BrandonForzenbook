package com.brandon.createaccount.core.data.mocks

import android.util.Log
import com.brandon.createaccount.core.data.CreateUserData
import com.brandon.createaccount.core.data.CreateUserRepository
import com.brandon.createaccount.core.data.UserAlreadyExistsException
import com.brandon.createaccount.core.data.mocks.CreateUserRepoAlwaysSuccessMock.Companion.REPOSITORY_MOCK_TAG

class CreateUserRepoAlwaysSuccessMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.d(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    companion object {
        const val REPOSITORY_MOCK_TAG = "Brandon_Test_Data"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Data Mock."
    }
}

class CreateUserRepoAlwaysFailsMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.d(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(FAILURE_MOCK_MESSAGE)
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class CreateUserRepoAlwaysThrowsMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.d(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}

class CreateUserRepoAlwaysThrowsDupeMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.d(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw UserAlreadyExistsException(ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}