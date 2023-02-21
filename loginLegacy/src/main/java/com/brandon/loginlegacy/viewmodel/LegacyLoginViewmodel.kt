package com.brandon.loginlegacy.viewmodel

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LegacyLoginViewmodel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager,
) : LoginViewModel() {

    override var state: LoginUiState
        get() = _uiState.value
        set(value) { _uiState.value = value }

    private var _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(state)
    private val uiState: LoginUiState
        get() = _uiState.value
    var onUpdate: ((LoginUiState) -> Unit)? = null

    init {
        viewModelScope.launch {
            _uiState.collect {
                _uiState.value = it
                Log.e(VIEWMODEL_ERROR_TAG, "$state")
            }
        }
        onUpdate?.invoke(uiState)
    }

}