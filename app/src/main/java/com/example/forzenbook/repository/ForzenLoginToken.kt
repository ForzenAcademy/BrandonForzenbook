package com.example.forzenbook.repository

import com.example.forzenbook.data.ForzenEntity

data class LoginToken(
    val token: String
)

fun ForzenEntity.toLoginToken(): LoginToken {
    return LoginToken(
        token = token ?: "Brandon_Test_Repository Missing Token"
    )
}