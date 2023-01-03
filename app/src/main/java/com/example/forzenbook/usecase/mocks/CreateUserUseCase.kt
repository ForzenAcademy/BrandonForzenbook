package com.example.forzenbook.usecase.mocks

import com.example.forzenbook.repository.CreateUserErrors
import com.example.forzenbook.usecase.CreateUserUseCase
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