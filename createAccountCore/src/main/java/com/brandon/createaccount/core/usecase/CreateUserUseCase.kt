package com.brandon.createaccount.core.usecase

import com.brandon.utilities.CreateAccountValidationState

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: String,
        location: String
    ): CreateAccountValidationState

}