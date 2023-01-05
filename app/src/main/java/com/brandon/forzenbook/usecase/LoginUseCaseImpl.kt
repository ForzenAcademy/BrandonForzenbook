package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.ForzenRepository

class LoginUseCaseImpl(
    private val repository: ForzenRepository,
) : LoginUseCase, AccountValidation() {

    override suspend fun invoke(email: String, code: String?): LoginValidationState {
        val validEmail = isValidEmail(email)
        if (code == null) {
            return if (validEmail) {
                try {
                    repository.getCode(email)
                    LoginValidationState.CodeSent
                } catch (e: Exception) {
                    LoginValidationState.LoginError(genericError = true)
                }
            } else LoginValidationState.LoginError(emailError = true)
        } else {
            val validCode = isValidCode(code)
            return if (validEmail && validCode) {
                try {
                    repository.getToken(email, code)
                    LoginValidationState.Success
                } catch (e: Exception) {
                    LoginValidationState.LoginError(genericError = true)
                }
            } else {
                LoginValidationState.LoginError(emailError = validEmail, codeError = validCode)
            }
        }
    }
}