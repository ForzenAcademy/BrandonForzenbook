package com.brandon.forzenbook.usecase

interface LoginUseCase {

    suspend operator fun invoke(email: String, code: String?): LoginValidationState

}