package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.UserAlreadyExistsException

class CreateUserUseCaseImpl(
    private val repository: ForzenRepository,
) : CreateUserUseCase, AccountValidation() {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        date: String,
        location: String
    ): CreateAccountValidationState {
        val formattedDate = isValidDateOfBirth(date)
        val validEmail = isValidEmail(email)
        val validLocation = isValidStringInput(location)
        return if (validEmail && validLocation && formattedDate != null) {
            try {
                val createUserData = CreateUserData(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    dateOfBirth = formattedDate,
                    location = location
                )
                repository.createUser(createUserData)
                CreateAccountValidationState.Success
            } catch (e: UserAlreadyExistsException) {
                CreateAccountValidationState.CreateAccountDuplicateError
            } catch (e: Exception) {
                CreateAccountValidationState.CreateAccountError(genericError = true)
            }
        } else {
            CreateAccountValidationState.CreateAccountError(
                emailError = validEmail,
                locationError = validLocation,
                dateOfBirthError = formattedDate != null,
            )
        }
    }
}