package com.brandon.forzenbook.repository.mocks

import android.util.Log
import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.CreateUserRepository
import com.brandon.forzenbook.repository.mocks.CreateUserRepoAlwaysSuccessMock.Companion.REPOSITORY_MOCK_TAG
import com.brandon.logincore.data.mocks.LoginRepositoryAlwaysThrowsMock
import com.brandon.logincore.data.mocks.LoginRepositoryNeverReturnsMock

class CreateUserRepoAlwaysSuccessMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, SUCCESS_MOCK_MESSAGE)
        return
    }

    companion object {
        const val REPOSITORY_MOCK_TAG = "Brandon_Test_Data"
        const val SUCCESS_MOCK_MESSAGE = "Always Success Data Mock."
    }
}

class CreateUserRepoAlwaysFailsMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, FAILURE_MOCK_MESSAGE)
        throw Exception(LoginRepositoryNeverReturnsMock.FAILURE_MOCK_MESSAGE)
    }

    companion object {
        const val FAILURE_MOCK_MESSAGE = "Always Fails Network Mock."
    }
}

class CreateUserRepoAlwaysThrowsMock : CreateUserRepository {

    override suspend fun createUser(createUserData: CreateUserData) {
        Log.e(REPOSITORY_MOCK_TAG, ALWAYS_THROWS_MOCK_MESSAGE)
        throw Exception(LoginRepositoryAlwaysThrowsMock.ALWAYS_THROWS_MOCK_MESSAGE)
    }

    companion object {
        const val ALWAYS_THROWS_MOCK_MESSAGE = "Always Throws Exception Mock."
    }
}