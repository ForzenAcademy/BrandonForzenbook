package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.LoginToken

class LoginUseCaseImpl(
    private val repository: ForzenRepository,
) : LoginUseCase {

    override suspend fun invoke(email: String, code: String): Boolean {
        return try {
            repository.getToken(email, code)
            true
        } catch (e: Exception) {
            false
        }
    }
}