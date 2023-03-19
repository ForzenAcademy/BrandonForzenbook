package com.brandon.loginfragments.viewmodel

import android.net.ConnectivityManager
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewmodel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager,
) : LoginViewModel() {

    // TODO FA-125 Handle Any Navigation

    override var state: LoginUiState
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }

    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()
}