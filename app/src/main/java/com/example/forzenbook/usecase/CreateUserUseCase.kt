package com.example.forzenbook.usecase

import com.example.forzenbook.repository.CreateUserErrors
import java.sql.Date

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserErrors

}