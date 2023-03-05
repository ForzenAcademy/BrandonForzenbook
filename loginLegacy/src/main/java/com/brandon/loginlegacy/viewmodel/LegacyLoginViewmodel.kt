package com.brandon.loginlegacy.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import com.brandon.navigation.LegacyNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LegacyLoginViewmodel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager,
    private val navigation: LegacyNavigation,
) : LoginViewModel() {

    override var state: LoginUiState
        get() = _uiState.value
        set(value) { _uiState.value = value }

    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    fun createAccountClicked(context: Context) {
        navigation.navigateToCreateAccount(context)
    }

    fun loginButtonClicked(context: Context) {
        navigation.navigateToLandingPage(context)
    }
}