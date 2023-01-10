package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.UserAlreadyExistsException
import com.brandon.forzenbook.viewmodels.CreateUserOutcome
import java.sql.Date

class CreateUserUseCaseImpl(
    private val repository: ForzenRepository,
) : CreateUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: Date,
        location: String
    ): CreateUserOutcome {
        val createUserData = CreateUserData(
            firstName = firstName,
            lastName = lastName,
            email = email,
            dateOfBirth = date,
            location = location
        )
        return try {
            repository.createUser(createUserData)
            CreateUserOutcome.CREATE_USER_SUCCESS
        } catch (e: UserAlreadyExistsException) {
            CreateUserOutcome.CREATE_USER_DUPLICATE
        } catch (e: Exception) {
            CreateUserOutcome.CREATE_USER_FAILURE
        }
    }
}