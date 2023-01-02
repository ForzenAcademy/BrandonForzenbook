package com.example.forzenbook.usecase

import com.example.forzenbook.repository.CreateUserData
import com.example.forzenbook.repository.ForzenRepository
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
    ): Boolean {
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