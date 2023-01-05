package com.brandon.forzenbook.repository

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CreateUserData(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    @SerializedName("birth_date") val dateOfBirth: Date,
    val location: String,
)