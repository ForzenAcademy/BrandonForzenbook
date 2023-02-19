package com.brandon.loginlegacy.viewmodel

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.brandon.logincore.usecase.LoginUseCase
import com.brandon.logincore.viewmodel.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LegacyLoginViewmodel @Inject constructor(
    override val loginUseCase: LoginUseCase,
    override val connectivityManager: ConnectivityManager,
) : LoginViewModel() {

}