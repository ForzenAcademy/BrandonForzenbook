package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.CreateUserErrors
import com.brandon.forzenbook.repository.ForzenRepository
import java.sql.Date

class CreateUserUseCaseImpl(
    private val repository: ForzenRepository,
) : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserErrors {
        val createUserData = CreateUserData(
            firstName = firstName,
            lastName = lastName,
            password = password,
            email = email,
            date = date,
            location = location
        )
        return repository.createUser(createUserData)
    }

}