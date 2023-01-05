package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.LoginToken

interface LoginUseCase {

    suspend operator fun invoke(userName: String, password: String): LoginToken?

}