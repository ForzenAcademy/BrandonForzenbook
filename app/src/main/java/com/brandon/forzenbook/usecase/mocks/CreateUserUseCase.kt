package com.brandon.forzenbook.usecase.mocks

import com.brandon.forzenbook.repository.CreateUserErrors
import com.brandon.forzenbook.usecase.CreateUserUseCase
import java.sql.Date

class CreateUserUseCaseAlwaysReturns() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserErrors {
        return CreateUserErrors()
    }

}

class CreateUserUseCaseAlwaysThrows() : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserErrors {
        throw Exception("Brandon_Test_UseCase Always Throws Mock")
    }

}