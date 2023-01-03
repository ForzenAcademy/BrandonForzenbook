package com.example.forzenbook.repository

data class CreateUserErrors(
    val isNetworkError: Boolean = false,
    val isDataError: Boolean = false,
    val isServiceError: Boolean = false,
)
