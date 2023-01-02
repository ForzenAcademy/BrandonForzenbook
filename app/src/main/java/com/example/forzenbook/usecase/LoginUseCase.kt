package com.example.forzenbook.usecase

import com.example.forzenbook.repository.LoginToken

interface LoginUseCase {

    suspend operator fun invoke(userName: String, password: String): LoginToken?

}