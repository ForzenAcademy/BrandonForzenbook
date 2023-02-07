package com.brandon.utilities

import androidx.core.util.PatternsCompat
import java.sql.Date
import java.text.SimpleDateFormat

abstract class AccountValidation {

    protected fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    protected fun isValidCode(code: String): Boolean {
        return code.isNumeric() && code.length == CODE_CHAR_LIMIT
    }

    protected fun isValidStringInput(input: String): Boolean {
        return input.length <= LOCATION_CHAR_LIMIT && input.isNotEmpty()
    }

    protected fun isValidDateOfBirth(dob: String): Date? {
        val date = dob.filter { it != '/' && it != '-' }
        date.forEach {
            if (!it.isDigit()) {
                return null
            }
        }
        return try {
            val inputFormat = SimpleDateFormat(INCOMING_DATE_FORMAT)
            val tempDate = inputFormat.parse(date)
            val outputFormat = SimpleDateFormat(OUTGOING_DATE_FORMAT)
            val dateString = tempDate?.let { outputFormat.format(it) }
            Date.valueOf(dateString)
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val CODE_CHAR_LIMIT = 6
        const val LOCATION_CHAR_LIMIT = 64
        const val INCOMING_DATE_FORMAT = "MMddyyyy"
        const val OUTGOING_DATE_FORMAT = "yyyy-MM-dd"
    }
}

sealed interface CreateAccountValidationState {

    data class CreateAccountError(
        val emailError: Boolean = false,
        val locationError: Boolean = false,
        val dateOfBirthError: Boolean = false,
        val genericError: Boolean = false,
    ) : CreateAccountValidationState

    object CreateAccountDuplicateError : CreateAccountValidationState

    object Success : CreateAccountValidationState
}

sealed interface LoginValidationState {

    data class LoginError(
        val emailError: Boolean = false,
        val codeError: Boolean = false,
        val genericError: Boolean = false,
    ) : LoginValidationState

    object CodeSent : LoginValidationState

    object Success : LoginValidationState

}