package com.brandon.forzenbook.network

import com.brandon.forzenbook.repository.CreateUserData
import java.sql.Date

data class CreateAccountRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val date: Date,
    val location: String,
)

fun CreateUserData.toCreateAccountRequest(): CreateAccountRequest {
    return CreateAccountRequest(
        firstName = firstName,
        lastName = lastName,
        email = email,
        date = dateOfBirth,
        location = location
    )
}