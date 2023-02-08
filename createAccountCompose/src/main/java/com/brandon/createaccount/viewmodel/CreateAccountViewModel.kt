package com.brandon.createaccount.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.utilities.CreateAccountValidationState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
) : ViewModel() {

    private val _createAccountState: MutableState<CreateAccountUiState> =
        mutableStateOf(CreateAccountUiState.Idle())
    val createAccountState: MutableState<CreateAccountUiState>
        get() = _createAccountState

    sealed interface CreateAccountUiState {

        data class Idle(
            val email: String = "",
            val firstName: String = "",
            val lastName: String = "",
            val location: String = "",
            val dateOfBirth: String = "",
            val isFirstNameError: Boolean = false,
            val isLastNameError: Boolean = false,
            val isEmailError: Boolean = false,
            val isLocationError: Boolean = false,
            val isDateError: Boolean = false,
            val isDuplicateUser: Boolean = false,
        ) : CreateAccountUiState

        data class Loading(
            val email: String = "",
            val firstName: String = "",
            val lastName: String = "",
            val location: String = "",
            val dateOfBirth: String = "",
        ) : CreateAccountUiState

        object Loaded : CreateAccountUiState
    }

    fun createAccountClicked(
        firstName: String,
        lastName: String,
        email: String,
        date: String,
        location: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            createAccountState.value = CreateAccountUiState.Loading(
                email = email,
                firstName = firstName,
                lastName = lastName,
                location = location,
                dateOfBirth = date,
            )
            val outcome = createUserUseCase(
                firstName = firstName,
                lastName = lastName,
                email = email,
                date = date,
                location = location
            )
            Log.e(VIEWMODEL_ERROR_TAG, "$outcome")
            createAccountState.value = when (outcome) {
                is CreateAccountError -> {
                    CreateAccountUiState.Idle(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        location = location,
                        dateOfBirth = date,
                        isEmailError = outcome.emailError,
                        isLocationError = outcome.locationError,
                        isDateError = outcome.dateOfBirthError,
                        isFirstNameError = outcome.firstNameError,
                        isLastNameError = outcome.lastNameError
                    )
                }
                is CreateAccountDuplicateError -> {
                    CreateAccountUiState.Idle(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        location = location,
                        dateOfBirth = date,
                        isDuplicateUser = true,
                    )
                }
                is Success -> {
                    CreateAccountUiState.Loaded
                }
            }
        }
        Log.e(VIEWMODEL_ERROR_TAG, "${createAccountState.value}")
    }

    companion object {
        const val VIEWMODEL_ERROR_TAG = "Brandon_Test_ViewModel"
        const val VIEW_ERROR_TAG = "Brandon_Test_View"
        const val KEYBOARD_ERROR = "Error Hiding Keyboard"
    }
}
