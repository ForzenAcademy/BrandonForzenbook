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
            val isUserNameError: Boolean = false,
            val isPasswordError: Boolean = false,
            val isInvalidCredentialsError: Boolean = false,
        ): LoginUiState

        object Loading: LoginUiState

        data class Loaded(
            val loginToken: String,
        ): LoginUiState

    }

    fun login(userName: String, password: String) {
        loginUserName = userName
        loginPassword = password
        viewModelScope.launch {
            state.value = LoginUiState.Loading
            delay(5000)
            val isValidName = isValidUserName(userName)
            val isValidPass = isValidPassword(password)
            if (isValidName || isValidPass) {
                state.value = LoginUiState.Error(
                    isUserNameError = isValidName,
                    isPasswordError = isValidPass,
                )
                cancel()
            }
            val token = loginUseCase(
                "Brandon_Test_Network Login Request Mock.",
                "Brandon_Test_Network Login Request Mock.",
            )
            if (token == null) {
                state.value = LoginUiState.Error(
                    isInvalidCredentialsError = true,
                )
                cancel()
            } else {
                state.value = LoginUiState.Loaded(
                    loginToken = token.token
                )
            }
            println("Brandon_Test_ViewModel $token")
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

//(Regex("[A-Za-z0-9]"))