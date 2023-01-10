package com.brandon.forzenbook.repository

class UserAlreadyExistsException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}