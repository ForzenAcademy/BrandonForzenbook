package com.brandon.createaccount.core.usecase

import com.brandon.createaccount.core.data.*
import com.brandon.utilities.AccountValidation
import com.brandon.utilities.CreateAccountValidationState
import com.brandon.utilities.CreateAccountValidationState.*

class CreateUserUseCaseImpl(
    private val repository: CreateUserRepository,
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
        val validFirstName = isValidName(firstName)
        val validLastName = isValidName(lastName)
        return if (validEmail && validLocation && formattedDate != null && validFirstName && validLastName) {
            try {
                val createUserData = CreateUserData(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    dateOfBirth = formattedDate,
                    location = location
                )
                repository.createUser(createUserData)
                Success
            } catch (e: UserAlreadyExistsException) {
                CreateAccountDuplicateError
            } catch (e: InvalidFirstNameException) {
                CreateAccountError(firstNameError = true)
            } catch (e: InvalidLastNameException) {
                CreateAccountError(lastNameError = true)
            } catch (e: InvalidEmailException) {
                CreateAccountError(emailError = true)
            } catch (e: InvalidLocationException) {
                CreateAccountError(locationError = true)
            } catch (e: InvalidBirthDateException) {
                CreateAccountError(dateOfBirthError = true)
            } catch (e: Exception) {
                CreateAccountError(genericError = true)
            }
        } else {
            CreateAccountError(
                emailError = !validEmail,
                locationError = !validLocation,
                dateOfBirthError = formattedDate == null,
                firstNameError = !validFirstName,
                lastNameError = !validLastName
            )
        }
    }
}