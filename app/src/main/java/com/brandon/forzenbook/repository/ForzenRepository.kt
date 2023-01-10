package com.brandon.forzenbook.repository

interface ForzenRepository {

    suspend fun getCode(email: String)

    suspend fun getToken(email: String, code: String)

    suspend fun createUser(createUserData: CreateUserData)

}