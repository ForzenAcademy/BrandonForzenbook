package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.CreateUserErrors
import java.sql.Date

interface CreateUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): Boolean

}