package com.brandon.logincompose.viewmodel

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeLoginViewModel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager
) : LoginViewModel() {

    private var _uiState: MutableState<LoginUiState> = mutableStateOf(this.state.value)
    val uiState: MutableState<LoginUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            this@ComposeLoginViewModel.state.collect {
                _uiState.value = it
                Log.e(VIEWMODEL_ERROR_TAG, "${state.value}")
            }
        }
    }

}