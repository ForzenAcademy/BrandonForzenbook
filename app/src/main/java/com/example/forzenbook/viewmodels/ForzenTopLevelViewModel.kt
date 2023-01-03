package com.example.forzenbook.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forzenbook.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForzenTopLevelViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var loginUserName: String = ""
    var loginPassword: String = ""

    private val _state: MutableState<LoginUiState> = mutableStateOf(LoginUiState.Idle)
    val state: MutableState<LoginUiState>
        get() = _state

    sealed interface LoginUiState {

        object Idle: LoginUiState

        data class Error(
            val isGenericError: Boolean = false,
            val isUserNameError: Boolean = false,
            val isPasswordError: Boolean = false,
            val isInvalidCredentialsError: Boolean = false,
            val isNetworkError: Boolean = false,
            val isDataError: Boolean = false,
            val isServiceError: Boolean = false,
        ): LoginUiState

        object Loading: LoginUiState

        data class Loaded(
            val loginToken: String,
        ): LoginUiState

    }

    fun dismissNotification() {
        state.value = LoginUiState.Idle
    }

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            state.value = LoginUiState.Loading
            loginUserName = userName
            loginPassword = password
            delay(5000)
            if (userName.isEmpty() || password.isEmpty()) {
                state.value = LoginUiState.Error(
                    isInvalidCredentialsError = true,
                )
                cancel()
            }
            if (!isValidUserName(userName)) {
                state.value = LoginUiState.Error(
                    isUserNameError = true,
                )
                cancel()
            }
            if (!isValidPassword(password)) {
                state.value = LoginUiState.Error(
                    isPasswordError = true,
                )
                cancel()
            }
            val token = loginUseCase(userName, password)
            if (token != null) {
                if (token.isDatabaseError && token.isServiceError) {
                    state.value = LoginUiState.Error(isServiceError = true, isDataError = true)
                }
                else if (token.isNetworkError) {
                    state.value = LoginUiState.Error(isServiceError = true, isDataError = true)
                }
                else if (token.isServiceError) {
                    state.value = LoginUiState.Error(isServiceError = true, isDataError = true)
                }
                else if (token.isDatabaseError) {
                    state.value = LoginUiState.Error(isServiceError = true, isDataError = true)
                } else {
                    if (token.token != null) {
                        state.value = LoginUiState.Loaded(loginToken = token.token)
                    }
                    else { state.value = LoginUiState.Error(isGenericError = true) }
                }
            }
            println("Brandon_Test_ViewModel ${state.value}")
        }
    }

    private fun isValidUserName(name: String): Boolean {
        return if(name.length > 20) {
            false
        }
        else if(!name.matches((Regex("[A-Za-z0-9]")))) {
            false
        } else name.matches((Regex("[A-Za-z0-9]")))
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length <= 30
    }

    companion object {
        const val USERNAME_CHAR_LIMIT = 20
        const val PASSWORD_CHAR_LIMIT = 30
    }
}