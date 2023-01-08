package com.brandon.forzenbook.network

data class LoginRequest(
    val userName: String,
    val code: String,
)