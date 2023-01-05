package com.brandon.forzenbook.repository

import com.brandon.forzenbook.data.ForzenEntity

data class LoginToken(
    val token: String?,
)

fun ForzenEntity.toLoginToken(): LoginToken {
    return LoginToken(
        token = token,
    )
}