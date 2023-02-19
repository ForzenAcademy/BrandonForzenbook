package com.brandon.logincompose.viewmodel

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeLoginViewModel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager
) : LoginViewModel() {

    private var _state: LoginUiState = LoginUiState.Idle()
    override var state: LoginUiState
        get() = _state
        set(value) {
            _state = value
            _uiState.value = state
            Log.e(VIEWMODEL_ERROR_TAG, "$state")
        }

    private var _uiState: MutableState<LoginUiState> = mutableStateOf(state)
    val uiState: MutableState<LoginUiState>
        get() = _uiState

}