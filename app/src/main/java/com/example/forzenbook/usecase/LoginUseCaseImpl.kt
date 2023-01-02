package com.example.forzenbook.usecase

import com.example.forzenbook.repository.ForzenRepository
import com.example.forzenbook.repository.LoginToken
import kotlinx.coroutines.delay

class LoginUseCaseImpl(
    private val repository: ForzenRepository,
): LoginUseCase {

    override suspend fun invoke(userName: String, password: String): LoginToken? {
        return repository.getToken(userName, password)
    }

}