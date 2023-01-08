package com.brandon.forzenbook.viewmodels

import android.util.Log
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
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.Companion.VIEWMODEL_ERROR_TAG
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

    private var sqlDate: Date? = null

    private val _state: MutableState<CreateAccountUiState> =
        mutableStateOf(CreateAccountUiState.Idle)
    val state: MutableState<CreateAccountUiState>
        get() = _state

    sealed interface CreateAccountUiState {

        object Idle : CreateAccountUiState

        data class Error(
            val isEmailError: Boolean = false,
            val isLocationError: Boolean = false,
            val isDateError: Boolean = false,
            val isNetworkError: Boolean = false,
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
        email: String,
        date: String,
        location: String
    ) {
        viewModelScope.launch {
            state.value = CreateAccountUiState.Loading
            delay(5000)
            val validMail = isValidEmail(email)
            val validLoc = isValidLocation(location)
            val validDob = isValidDateOfBirth(date)
            if (validMail && validLoc && validDob && sqlDate != null) {
                sqlDate?.let { sqlDate ->
                    val outcome = createUserUseCase(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        date = sqlDate,
                        location = location
                    )
                    if (!outcome) {
                        state.value = CreateAccountUiState.Error(isNetworkError = true)
                    } else {
                        state.value = CreateAccountUiState.Loaded
                    }
                }
            } else {
                state.value = CreateAccountUiState.Error(
                    isEmailError = validMail,
                    isLocationError = validLoc,
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
            Log.e(VIEWMODEL_ERROR_TAG, "Invalid Date of Birth Provided.")
            false
        }
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
    }
}