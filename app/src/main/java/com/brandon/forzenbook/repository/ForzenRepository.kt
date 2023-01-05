package com.brandon.forzenbook.repository

interface ForzenRepository {

    suspend fun getToken(userName: String, code: String): LoginToken?

    suspend fun createUser(createUserData: CreateUserData): Boolean

}