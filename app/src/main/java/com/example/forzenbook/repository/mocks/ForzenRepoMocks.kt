package com.example.forzenbook.repository.mocks

import com.example.forzenbook.repository.CreateUserData
import com.example.forzenbook.repository.CreateUserErrors
import com.example.forzenbook.repository.ForzenRepository
import com.example.forzenbook.repository.LoginToken

class ForzenRepositoryAlwaysReturnsMock() : ForzenRepository {
    override suspend fun getToken(userName: String, password: String): LoginToken? {
        return LoginToken(
            token = "ThisIsASixtyFour(64)CharacterLongStringForTestingNetworkResponse"
        )
    }

    override suspend fun createUser(createUserData: CreateUserData): CreateUserErrors {
        return CreateUserErrors()
    }

}

class ForzenRepositoryNeverReturnsMock() : ForzenRepository {
    override suspend fun getToken(userName: String, password: String): LoginToken? {
        return LoginToken(null)
    }

    override suspend fun createUser(createUserData: CreateUserData): CreateUserErrors {
        return CreateUserErrors(isNetworkError = true)
    }

}

class ForzenRepositoryAlwaysThrowsMock() : ForzenRepository {
    override suspend fun getToken(userName: String, password: String): LoginToken? {
        throw Exception("Brandon_Test_Mock Throw Repo Mock")
    }

    override suspend fun createUser(createUserData: CreateUserData): CreateUserErrors {
        throw Exception("Brandon_Test_Mock Throw Repo Mock")
    }

}