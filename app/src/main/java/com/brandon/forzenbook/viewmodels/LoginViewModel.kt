package com.brandon.forzenbook.viewmodels

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.utilities.LoginValidationState
import com.brandon.utilities.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val connectivityManager: ConnectivityManager,
) : ViewModel() {

    private val _uiState: MutableState<LoginUiState> = mutableStateOf(LoginUiState.Idle())
    val uiState: MutableState<LoginUiState>
        get() = _uiState

    sealed interface LoginUiState {

        data class Idle(
            val email: String = "",
            val isEmailError: Boolean = false,
            val isGenericError: Boolean = false,
            val isCodeSent: Boolean = false,
        ) : LoginUiState

        data class Loading(
            val email: String = "",
            val isCodeSent: Boolean = false,
        ) : LoginUiState

        // TODO remove this once we have another screen FA-83
        object LoggedIn : LoginUiState

    }

    fun checkInternetConnection(): Boolean {
        return connectivityManager.isConnected()
    }

    fun loginClicked(email: String, code: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = LoginUiState.Loading(isCodeSent = true, email = email)
            val outcome = loginUseCase(email, code)
            uiState.value = when (outcome) {
                is LoginValidationState.Success -> {
                    LoginUiState.LoggedIn
                }
                is LoginValidationState.CodeSent -> {
                    LoginUiState.Idle(isCodeSent = true, email = email)
                }
                is LoginValidationState.LoginError -> {
                    LoginUiState.Idle(
                        isCodeSent = code != null,
                        email = email,
                        isEmailError = outcome.emailError,
                        isGenericError = outcome.genericError
                    )
                }
            }
        }
        Log.e(VIEWMODEL_ERROR_TAG, "${uiState.value}")
    }

    companion object {
        const val VIEWMODEL_ERROR_TAG = "Brandon_Test_ViewModel"
    }
}