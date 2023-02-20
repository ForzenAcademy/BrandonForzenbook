package com.brandon.createaccount.core.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.utilities.CreateAccountValidationState.*
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.CreateAccountUiState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CreateAccountViewModel : ViewModel() {

    protected abstract val createUserUseCase: CreateUserUseCase
    protected abstract var state: CreateAccountUiState

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
            state = Loading(
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
            state = when (outcome) {
                is CreateAccountError -> Idle(
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
                is CreateAccountDuplicateError -> {
                    Idle(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        location = location,
                        dateOfBirth = date,
                        isDuplicateUser = true,
                    )
                }
                is Success -> Loaded
            }
        }
    }

    companion object {
        const val VIEWMODEL_ERROR_TAG = "Brandon_Test_ViewModel"
        const val VIEW_ERROR_TAG = "Brandon_Test_View"
        const val KEYBOARD_ERROR = "Error Hiding Keyboard"
    }
}
