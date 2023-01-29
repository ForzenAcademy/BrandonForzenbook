package com.brandon.forzenbook.repository

interface CreateUserRepository {

    suspend fun createUser(createUserData: CreateUserData)

}