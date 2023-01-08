package com.brandon.forzenbook.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.forzenbook.usecase.LoginUseCase
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
    var logincode: String = ""

    private val _state: MutableState<LoginUiState> = mutableStateOf(LoginUiState.PreCode)
    val state: MutableState<LoginUiState>
        get() = _state

    sealed interface LoginUiState {

        object PreCode : LoginUiState

        data class Error(
            val isGenericError: Boolean = false,
            val isEmailError: Boolean = false,
            val isCodeError: Boolean = false,
            val isInvalidCredentialsError: Boolean = false,
            val isNetworkError: Boolean = false,
        ) : LoginUiState

        object Loading : LoginUiState

        data class PostCodeSent(
            val loginToken: String,
        ) : LoginUiState

    }

    fun dismissNotification() {
        state.value = LoginUiState.PreCode
    }

    fun loginClicked(userName: String, code: String) {
        viewModelScope.launch {
            state.value = LoginUiState.Loading
            loginUserName = userName
            logincode = code
            delay(5000)
            if (userName.isEmpty() || code.isEmpty()) {
                state.value = LoginUiState.Error(
                    isInvalidCredentialsError = true,
                )
                cancel()
            }
            val token = loginUseCase(userName, code)
            if (token != null) {
                if (token.token != null) {
                    state.value = LoginUiState.PostCodeSent(loginToken = token.token)
                } else {
                    state.value = LoginUiState.Error(isGenericError = true)
                }
            }
            Log.e(VIEWMODEL_ERROR_TAG, "${state.value}")
        }
    }

    fun isValidCode(code: String): Boolean {
        return code.isDigitsOnly()
    }

    suspend fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    companion object {
        const val USERNAME_CHAR_LIMIT = 20
        const val CODE_CHAR_LIMIT = 30

        const val VIEWMODEL_ERROR_TAG = "Brandon_Test_ViewModel"
    }
}