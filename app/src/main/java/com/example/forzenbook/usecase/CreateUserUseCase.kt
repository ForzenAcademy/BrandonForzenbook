package com.example.forzenbook.usecase

import java.sql.Date

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: Date,
        location: String
    ): Boolean

}