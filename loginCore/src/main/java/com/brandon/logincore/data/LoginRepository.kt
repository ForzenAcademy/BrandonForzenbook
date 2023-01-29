package com.brandon.logincore.data

interface LoginRepository {

    suspend fun getCode(email: String)

    suspend fun getToken(email: String, code: String)

}