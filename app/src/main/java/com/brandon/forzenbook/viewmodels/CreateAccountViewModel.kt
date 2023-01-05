package com.brandon.forzenbook.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.forzenbook.usecase.CreateAccountValidationState
import com.brandon.forzenbook.usecase.CreateUserUseCase
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
            Log.e(LoginViewModel.VIEWMODEL_ERROR_TAG, "$outcome")
            createAccountState.value = when (outcome) {
                is CreateAccountValidationState.CreateAccountError -> {
                    CreateAccountUiState.Idle(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        location = location,
                        dateOfBirth = date,
                        isEmailError = outcome.emailError,
                        isLocationError = outcome.locationError,
                        isDateError = outcome.dateOfBirthError,
                    )
                }
                is CreateAccountValidationState.CreateAccountDuplicateError -> {
                    CreateAccountUiState.Idle(
                        email = email,
                        firstName = firstName,
                        lastName = lastName,
                        location = location,
                        dateOfBirth = date,
                        isDuplicateUser = true,
                    )
                }
                is CreateAccountValidationState.Success -> {
                    CreateAccountUiState.Loaded
                }
            }
        }
        Log.e(LoginViewModel.VIEWMODEL_ERROR_TAG, "${createAccountState.value}")
    }
}
