package com.brandon.forzenbook.usecase

import com.brandon.forzenbook.repository.ForzenRepository

class GetLoginCodeUseCaseImpl(
    private val forzenRepository: ForzenRepository
): GetLoginCodeUseCase {

    override suspend fun invoke(email: String): Boolean {
        return try {
            forzenRepository.getCode(email)
            true
        } catch (e: Exception) {
            false
        }
    }

}