package com.example.forzenbook.repository

import java.sql.Date

data class CreateUserData(
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val date: Date,
    val location: String,
)