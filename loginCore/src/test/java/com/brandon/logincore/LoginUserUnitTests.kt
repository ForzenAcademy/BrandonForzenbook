package com.brandon.logincore

import com.brandon.logincore.data.mocks.LoginRepositoryAlwaysReturnsMock
import com.brandon.logincore.data.mocks.LoginRepositoryNeverReturnsMock
import com.brandon.logincore.usecase.LoginUseCaseImpl
import com.brandon.utilities.LoginValidationState
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginUserUnitTests {
    @Test
    fun loginUseCaseSuccess() {
        val repoMock = LoginRepositoryAlwaysReturnsMock()
        val useCase = LoginUseCaseImpl(repoMock)
        runBlocking {
            Assert.assertEquals(
                LoginValidationState.CodeSent,
                useCase(email = TEST_EMAIL, null)
            )
        }
    }

    @Test
    fun loginUseCaseFails() {
        val repoMock = LoginRepositoryNeverReturnsMock()
        val useCase = LoginUseCaseImpl(repoMock)
        runBlocking {
            Assert.assertEquals(
                LoginValidationState.LoginError(genericError = true),
                useCase(email = TEST_EMAIL, null)
            )
        }
    }

    @Test
    fun loginUseCaseFailsInvalidEmail() {
        val repoMock = LoginRepositoryNeverReturnsMock()
        val useCase = LoginUseCaseImpl(repoMock)
        runBlocking {
            Assert.assertEquals(
                LoginValidationState.LoginError(emailError = true),
                useCase(email = "abc.com", null)
            )
        }
    }

    @Test
    fun loginUseCaseSuccessWithCode() {
        val repoMock = LoginRepositoryAlwaysReturnsMock()
        val useCase = LoginUseCaseImpl(repoMock)
        runBlocking {
            Assert.assertEquals(
                LoginValidationState.Success,
                useCase(email = TEST_EMAIL, TEST_CODE)
            )
        }
    }

    @Test
    fun loginUseCaseFailsWithCode() {
        val repoMock = LoginRepositoryNeverReturnsMock()
        val useCase = LoginUseCaseImpl(repoMock)
        runBlocking {
            Assert.assertEquals(
                LoginValidationState.LoginError(genericError = true),
                useCase(email = TEST_EMAIL, null)
            )
        }
    }


    companion object {
        const val TEST_EMAIL = "axaxotl@gmail.com"
        const val TEST_CODE = "123456"
    }
}