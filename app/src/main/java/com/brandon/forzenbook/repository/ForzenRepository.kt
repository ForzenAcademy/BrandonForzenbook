package com.brandon.forzenbook.repository

interface ForzenRepository {

    suspend fun getToken(userName: String, password: String): LoginToken?

    suspend fun createUser(createUserData: CreateUserData): CreateUserErrors

}