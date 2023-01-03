package com.example.forzenbook.repository

import com.example.forzenbook.data.ForzenEntity

data class LoginToken(
    val token: String?,
    val isNetworkError: Boolean = false,
    val isServiceError: Boolean = false,
    val isDatabaseError: Boolean = false,
)

fun ForzenEntity.toLoginToken(): LoginToken {
    return LoginToken(
        token = token,
    )
}