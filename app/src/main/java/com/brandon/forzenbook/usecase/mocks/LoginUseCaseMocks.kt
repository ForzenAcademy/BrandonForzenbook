package com.brandon.forzenbook.usecase.mocks

import com.brandon.forzenbook.repository.LoginToken
import com.brandon.forzenbook.usecase.LoginUseCase

class LoginUseCaseAlwaysReturns() : LoginUseCase {
    override suspend fun invoke(userName: String, password: String): LoginToken? {
        println("Brandon_Test_UseCase Always Returns Mock")
        return LoginToken("Brandon_Test_UseCase Always Returns Mock")
    }

}

class LoginUseCaseNeverReturns() : LoginUseCase {
    override suspend fun invoke(userName: String, password: String): LoginToken? {
        println("Brandon_Test_UseCase Never Returns Mock")
        return null
    }

}

class LoginUseCaseAlwaysThrows() : LoginUseCase {
    override suspend fun invoke(userName: String, password: String): LoginToken? {
        throw Exception("Brandon_Test_UseCase Always Throws Mock")
    }

}