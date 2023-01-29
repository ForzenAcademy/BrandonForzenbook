package com.brandon.logincore.usecase

import com.brandon.utilities.LoginValidationState

interface LoginUseCase {

    suspend operator fun invoke(email: String, code: String?): LoginValidationState

}