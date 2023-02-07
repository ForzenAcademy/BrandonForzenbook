package com.brandon.createaccount

import com.brandon.createaccount.core.data.mocks.CreateUserRepoAlwaysFailsMock
import com.brandon.createaccount.core.usecase.CreateUserUseCaseImpl
import com.brandon.createaccount.core.data.mocks.CreateUserRepoAlwaysSuccessMock
import com.brandon.createaccount.core.data.mocks.CreateUserRepoAlwaysThrowsDupeMock
import com.brandon.createaccount.core.data.mocks.CreateUserRepoAlwaysThrowsMock
import com.brandon.utilities.CreateAccountValidationState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CreateAccountUnitTests {


    @Test
    fun createUserUseCaseSuccess() {
        val repoMock = CreateUserRepoAlwaysSuccessMock()
        val useCase = CreateUserUseCaseImpl(repoMock)
        runBlocking {
            assertEquals(
                CreateAccountValidationState.Success,
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
        }
    }

    @Test
    fun createUserUseCaseFails() {
        val repoMock = CreateUserRepoAlwaysFailsMock()
        val useCase = CreateUserUseCaseImpl(repoMock)
        runBlocking {
            assertEquals(
                CreateAccountValidationState.CreateAccountError(genericError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
        }
    }

    @Test
    fun createUserUseCaseDuplicate() {
        val repoMock = CreateUserRepoAlwaysThrowsDupeMock()
        val useCase = CreateUserUseCaseImpl(repoMock)
        runBlocking {
            assertEquals(
                CreateAccountValidationState.CreateAccountDuplicateError,
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
        }
    }

    @Test
    fun createUserUseCaseRepoThrows() {
        val repoMock = CreateUserRepoAlwaysThrowsMock()
        val useCase = CreateUserUseCaseImpl(repoMock)
        runBlocking {
            assertEquals(
                CreateAccountValidationState.CreateAccountError(genericError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
        }
    }

    @Test
    fun createUserUseCaseValidateInputs() {
        val repoMock = CreateUserRepoAlwaysThrowsMock()
        val useCase = CreateUserUseCaseImpl(repoMock)
        runBlocking {
            assertEquals(
                CreateAccountValidationState.CreateAccountError(locationError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = "",
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(locationError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = "AAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH",
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(emailError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = "abc.com",
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(emailError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = "abc.@com",
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(emailError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = "@@@@",
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(emailError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = "abc.com",
                    location = TEST_LOCATION,
                    date = TEST_DATE_OF_BIRTH
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(dateOfBirthError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = "TEST_DATE_OF_BIRTH"
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(dateOfBirthError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = "12-34-1956"
                )
            )
            assertEquals(
                CreateAccountValidationState.CreateAccountError(dateOfBirthError = true),
                useCase(
                    firstName = TEST_FIRSTNAME,
                    lastName = TEST_LASTNAME,
                    email = TEST_EMAIL,
                    location = TEST_LOCATION,
                    date = "19360909"
                )
            )
        }
    }


    companion object {
        const val TEST_EMAIL = "axaxotl@gmail.com"
        const val TEST_FIRSTNAME = "Garen"
        const val TEST_LASTNAME = "Crownguard"
        const val TEST_DATE_OF_BIRTH = "12122000"
        const val TEST_LOCATION = "Androidvania"
    }
}