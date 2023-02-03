package com.brandon.network.login

data class LoginCodeResponse(
    val isCodeSent: Boolean = false,
    val isServiceError: Boolean = false,
)