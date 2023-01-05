package com.brandon.forzenbook.network

data class LoginCodeResponse(
    val isCodeSent: Boolean = false,
    val isServiceError: Boolean = false,
)