package com.brandon.forzenbook.usecase

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: String,
        location: String
    ): CreateAccountValidationState

}