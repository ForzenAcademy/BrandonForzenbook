package com.brandon.createaccountlegacy.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LegacyCreateAccountViewModel @Inject constructor(
    override val createUserUseCase: CreateUserUseCase
): CreateAccountViewModel() {

    override var state: CreateAccountUiState
        get() = _uiState.value
        set(value) { _uiState.value = value }

    private var _uiState: MutableStateFlow<CreateAccountUiState> = MutableStateFlow(CreateAccountUiState.Idle())
    val uiState: StateFlow<CreateAccountUiState>
        get() = _uiState.asStateFlow()

}