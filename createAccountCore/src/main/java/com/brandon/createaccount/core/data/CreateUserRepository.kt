package com.brandon.createaccount.core.data

interface CreateUserRepository {

    suspend fun createUser(createUserData: CreateUserData)

}