package com.brandon.createaccount.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComposeCreateAccountViewModel @Inject constructor(
    override val createUserUseCase: CreateUserUseCase,
) : CreateAccountViewModel() {

    private var _state: CreateAccountUiState = CreateAccountUiState.Idle()
    override var state: CreateAccountUiState
        get() = _state
        set(value) {
            _state = value
            _uiState.value = state
            Log.e(VIEWMODEL_ERROR_TAG, "$state")
        }

    private val _uiState: MutableState<CreateAccountUiState> =
        mutableStateOf(CreateAccountUiState.Idle())
    val uiState: MutableState<CreateAccountUiState>
        get() = _uiState


}
