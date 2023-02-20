package com.brandon.logincore.viewmodel

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel.LoginUiState.*
import com.brandon.utilities.LoginValidationState.*
import com.brandon.utilities.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class LoginViewModel : ViewModel() {

    protected abstract val loginUseCase: LoginUseCase
    protected abstract val connectivityManager: ConnectivityManager
    protected abstract var state: LoginUiState

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

        object LoggedIn : LoginUiState

    }

    fun checkInternetConnection(): Boolean {
        return connectivityManager.isConnected()
    }

    fun loginClicked(email: String, code: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            state = Loading(isCodeSent = true, email = email)
            val outcome = loginUseCase(email, code)
            state = when (outcome) {
                is Success -> LoggedIn
                is CodeSent -> Idle(
                    isCodeSent = true,
                    email = email
                )
                is LoginError -> Idle(
                    isCodeSent = code != null,
                    email = email,
                    isEmailError = outcome.emailError,
                    isGenericError = outcome.genericError
                )
            }
        }
    }

    companion object {
        const val VIEWMODEL_TAG = "Brandon_Test_ViewModel"
    }
}