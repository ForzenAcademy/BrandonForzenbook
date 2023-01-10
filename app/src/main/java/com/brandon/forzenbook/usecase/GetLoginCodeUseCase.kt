package com.brandon.forzenbook.usecase

interface GetLoginCodeUseCase {

    suspend operator fun invoke(email: String): Boolean

}