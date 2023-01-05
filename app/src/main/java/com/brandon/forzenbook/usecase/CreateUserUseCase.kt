package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.viewmodels.CreateUserOutcome
import java.sql.Date

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserOutcome

}