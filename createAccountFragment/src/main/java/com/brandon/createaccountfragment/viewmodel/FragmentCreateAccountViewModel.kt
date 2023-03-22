package com.brandon.createaccountfragment.viewmodel

import androidx.fragment.app.FragmentManager
import com.brandon.createaccount.core.usecase.CreateUserUseCase
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel
import com.brandon.navigation.FragmentNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FragmentCreateAccountViewModel @Inject constructor(
    override val createUserUseCase: CreateUserUseCase,
    private val navigation: FragmentNav,
) : CreateAccountViewModel() {

    override var state: CreateAccountUiState
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }

    private var _uiState: MutableStateFlow<CreateAccountUiState> =
        MutableStateFlow(CreateAccountUiState.Idle())
    val uiState: StateFlow<CreateAccountUiState>
        get() = _uiState.asStateFlow()

    fun loginRedirectClicked(fragmentManager: FragmentManager) =  navigation.navigateToLogin(fragmentManager)
}