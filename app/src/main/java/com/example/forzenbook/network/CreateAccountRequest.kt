package com.example.forzenbook.network

import com.example.forzenbook.repository.CreateUserData
import java.sql.Date

data class CreateAccountRequest(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val date: Date,
    val location: String,
)

fun CreateUserData.toCreateAccountRequest(): CreateAccountRequest {
    return CreateAccountRequest(
        firstName = firstName,
        lastName = lastName,
        password = password,
        email = email,
        date = date,
        location = location
    )
}