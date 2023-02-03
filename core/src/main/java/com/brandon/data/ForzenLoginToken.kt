package com.brandon.data

data class LoginToken(
    val token: String?,
)

fun ForzenEntity.toLoginToken(): LoginToken {
    return LoginToken(
        token = token,
    )
}