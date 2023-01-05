package com.brandon.forzenbook.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.forzenbook.usecase.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private var resetPasswordTimestamp: Long = 0L
    private var resetTimeRemaining: Int = 0
    private var isPasswordResetAvailable: Boolean = true

    private val _passwordState: MutableState<ResetPasswordState> =
        mutableStateOf(ResetPasswordState.Idle())
    val passwordState: MutableState<ResetPasswordState>
        get() = _passwordState

    sealed interface ResetPasswordState {
        data class Idle(
            val isOnCoolDown: Boolean = false,
            val timeRemaining: Int = 0
        ) : ResetPasswordState
        object Done : ResetPasswordState
    }

    fun calculateResetTimeRemaining() {
        viewModelScope.launch {
            while ((System.currentTimeMillis() - resetPasswordTimestamp) >= RESET_PASSWORD_TIMER) {
                delay(1000)
                passwordState.value = ResetPasswordState.Idle(timeRemaining = ((System.currentTimeMillis() - resetPasswordTimestamp)/1000).toInt())
            }
            isPasswordResetAvailable = true
        }
    }

    fun emailSent() {
        if (System.currentTimeMillis() - resetPasswordTimestamp >= RESET_PASSWORD_TIMER) {
            resetPasswordTimestamp = System.currentTimeMillis()
            passwordState.value = ResetPasswordState.Done
        }
    }

    private var sqlDate: Date? = null

    private val _state: MutableState<CreateAccountUiState> =
        mutableStateOf(CreateAccountUiState.Idle)
    val state: MutableState<CreateAccountUiState>
        get() = _state

    sealed interface CreateAccountUiState {

        object Idle : CreateAccountUiState

        data class Error(
            val isEmailError: Boolean = false,
            val isPasswordError: Boolean = false,
            val isLocationError: Boolean = false,
            val isDateError: Boolean = false,
            val isServiceError: Boolean = false,
            val isNetworkError: Boolean = false,
            val isDataError: Boolean = false,
        ) : CreateAccountUiState

        object Loading : CreateAccountUiState
        object Loaded : CreateAccountUiState
    }

    fun dismissErrorNotification() {
        state.value = CreateAccountUiState.Idle
    }

    fun createUser(
        firstName: String,
        lastName: String,
        password: String,
        email: String,
        date: String,
        location: String
    ) {
        viewModelScope.launch {
            state.value = CreateAccountUiState.Loading
            delay(5000)
            val validPass = isValidPassword(password)
            val validMail = isValidEmail(email)
            val validLoc = isValidLocation(location)
            val validDob = isValidDateOfBirth(date)
            if (validPass && validMail && validLoc && validDob && sqlDate != null) {
                sqlDate?.let { sqlDate ->
                    val outcome = createUserUseCase(
                        firstName = firstName,
                        lastName = lastName,
                        password = password,
                        email = email,
                        date = sqlDate,
                        location = location
                    )
                    if (outcome.isDataError) {
                        state.value = CreateAccountUiState.Error(isDataError = true)
                    }
                    else if (outcome.isNetworkError) {
                        state.value = CreateAccountUiState.Error(isNetworkError = true)
                    } else if (outcome.isServiceError) {
                        state.value = CreateAccountUiState.Error(isServiceError = true)
                    } else {
                        state.value = CreateAccountUiState.Loaded
                    }
                }
            } else {
                state.value = CreateAccountUiState.Error(
                    isEmailError = validMail,
                    isLocationError = validLoc,
                    isPasswordError = validPass,
                    isDateError = validDob,
                )
            }
        }
    }

    private fun isValidLocation(location: String): Boolean {
        return location.length <= LOCATION_CHAR_LIMIT
    }

    private fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidDateOfBirth(dob: String): Boolean {
        dob.forEach {
            if (!it.isDigit()) {
                return false
            }
        }
        return try {
            val inputFormat = SimpleDateFormat("MMddyyyy")
            val tempDate = inputFormat.parse(dob)
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateString = tempDate?.let { outputFormat.format(it) }
            sqlDate = Date.valueOf(dateString)
            true
        } catch (e: Exception) {
            println("Brandon_Test_ViewModel Invalid Date Provided.")
            false
        }
    }

    private fun isValidUserName(name: String): Boolean {
        return if (name.length > 20) {
            false
        } else if (!name.matches((Regex("[A-Za-z0-9]")))) {
            false
        } else name.matches((Regex("[A-Za-z0-9]")))
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length <= 30
    }


    class DateTransformation() : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            return dateFilter(text)
        }

        private fun dateFilter(text: AnnotatedString): TransformedText {

            val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
            var out = ""
            for (i in trimmed.indices) {
                out += trimmed[i]
                if (i % 2 == 1 && i < 4) out += "/"
            }

            val numberOffsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 1) return offset
                    if (offset <= 3) return offset + 1
                    if (offset <= 8) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset - 1
                    if (offset <= 10) return offset - 2
                    return 8
                }
            }

            return TransformedText(AnnotatedString(out), numberOffsetTranslator)
        }
    }

    companion object {
        const val EMAIL_CHAR_LIMIT = 30
        const val LOCATION_CHAR_LIMIT = 64
        const val RESET_PASSWORD_TIMER = 30000
    }
}