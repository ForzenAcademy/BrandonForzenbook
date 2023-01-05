package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.ForzenRepository
import com.brandon.forzenbook.repository.LoginToken

class LoginUseCaseImpl(
    private val repository: ForzenRepository,
) : LoginUseCase {

    override suspend fun invoke(userName: String, password: String): LoginToken? {
        return repository.getToken(userName, password)
    }
}