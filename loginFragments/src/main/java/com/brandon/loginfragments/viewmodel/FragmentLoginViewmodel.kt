package com.brandon.loginfragments.viewmodel

import android.net.ConnectivityManager
import androidx.fragment.app.FragmentManager
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import com.brandon.navigation.FragmentNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewmodel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager,
    private val navigation: FragmentNav,
) : LoginViewModel() {

    override var state: LoginUiState
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }

    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    fun loginButtonClicked(fragmentManager: FragmentManager) = navigation.navigateToLandingPage(fragmentManager)

    fun createAccountClicked(fragmentManager: FragmentManager) = navigation.navigateToCreateAccount(fragmentManager)

}