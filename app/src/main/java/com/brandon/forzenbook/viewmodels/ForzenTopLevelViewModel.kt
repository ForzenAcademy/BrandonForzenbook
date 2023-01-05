package com.brandon.forzenbook.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.forzenbook.usecase.GetLoginCodeUseCase
import com.brandon.forzenbook.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForzenTopLevelViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getLoginCodeUseCase: GetLoginCodeUseCase,
) : ViewModel() {

    private val _state: MutableState<LoginUiState> = mutableStateOf(LoginUiState.PreCode())
    val state: MutableState<LoginUiState>
        get() = _state

    sealed interface LoginUiState {

        data class PreCode(
            val isEmailError: Boolean = false,
            val isGenericError: Boolean = false,
        ) : LoginUiState

        object CodeSent : LoginUiState

        data class Error(
            val isEmailError: Boolean = false,
            val isGenericError: Boolean = false,
        ) : LoginUiState

        object Loading : LoginUiState

        object LoginWithCode : LoginUiState

    }

    fun getCode(email: String) {
        viewModelScope.launch {
            state.value = LoginUiState.Loading
            delay(5000)
            if (email.isNotEmpty()) {
                state.value = LoginUiState.PreCode(isEmailError = true)
                cancel()
            }
            if (getLoginCodeUseCase(email)) {
                state.value = LoginUiState.CodeSent
            } else {
                state.value = LoginUiState.PreCode(isGenericError = true)
            }
            Log.e(VIEWMODEL_ERROR_TAG, "${state.value}")
        }
    }

    fun loginClicked(email: String, code: String) {
        viewModelScope.launch {
            state.value = LoginUiState.Loading
            delay(5000)
            if (email.isEmpty() || code.isEmpty()) {
                state.value = LoginUiState.PreCode(isEmailError = true)
                cancel()
            }
            if (loginUseCase(email, code)) {
                state.value = LoginUiState.LoginWithCode
            } else {
                state.value = LoginUiState.Error(isGenericError = true)
            }
            Log.e(VIEWMODEL_ERROR_TAG, "${state.value}")
        }
    }

    fun isValidCode(code: String): Boolean {
        return code.isDigitsOnly()
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                return true
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
        const val VIEWMODEL_ERROR_TAG = "Brandon_Test_ViewModel"
        const val EMAIL_CHAR_LIMIT = 20
        const val CODE_CHAR_LIMIT = 6
    }
}

enum class CreateUserOutcome {
    CREATE_USER_SUCCESS,
    CREATE_USER_FAILURE,
    CREATE_USER_DUPLICATE,
}