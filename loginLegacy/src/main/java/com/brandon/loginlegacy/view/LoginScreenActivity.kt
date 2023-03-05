package com.brandon.loginlegacy.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.viewmodel.LoginViewModel
import com.brandon.logincore.viewmodel.LoginViewModel.Companion.VIEWMODEL_TAG
import com.brandon.logincore.viewmodel.LoginViewModel.LoginUiState.*
import com.brandon.loginlegacy.databinding.LoginScreenBinding
import com.brandon.loginlegacy.viewmodel.LegacyLoginViewmodel
import com.brandon.navigation.LegacyNavigation
import com.brandon.uicore.R
import com.brandon.uicore.connectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginScreenActivity : AppCompatActivity() {

    private val loginViewModel: LegacyLoginViewmodel by viewModels()
    private lateinit var binding: LoginScreenBinding
    @Inject
    lateinit var navigation: LegacyNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.viewModelScope.launch {
            loginViewModel.uiState.collect {
                updateUi(it)
                Log.e(VIEWMODEL_TAG, "${loginViewModel.uiState}")
            }
        }

        binding.loginButton.setOnClickListener {
            val emailField = binding.loginEmailField
            val codeField = binding.loginCodeField
            val fieldsError = binding.loginRequiredFieldsError
            fieldsError.isVisible = true
            emailField.clearFocus()
            emailField.isSelected = false
            val email = emailField.text.toString()
            val code = codeField.text.toString()
            if (email.isNotEmpty()) {
                if (code.isNotEmpty()) loginViewModel.loginClicked(email, code)
                else loginViewModel.loginClicked(email)
            } else fieldsError.isVisible = true
        }

        binding.createAccountRedirect.setOnClickListener {
            navigation.navigateToCreateAccount()
        }
    }

    private fun updateUi(uiState: LoginViewModel.LoginUiState) {
        binding.apply {
            when (uiState) {
                is Idle -> {
                    progressSpinner.isVisible = false
                    buttonText.isVisible = true
                    loginCodeField.apply {
                        isVisible = uiState.isCodeSent
                        isEnabled = true
                    }
                    loginEmailError.isVisible = uiState.isEmailError
                    loginButton.isEnabled = true
                    loginEmailField.apply {
                        isEnabled = true
                        setText(uiState.email)
                    }
                    buttonText.text =
                        if (uiState.isCodeSent) getString(R.string.core_get_code_text)
                        else getString(R.string.login_button_text)

                    if (uiState.isGenericError) connectionDialog(
                        this@LoginScreenActivity,
                        titleText = getString(R.string.core_error_no_internet_connection),
                        bodyText = getString(R.string.core_error_connect_and_try_again)
                    )
                }
                is Loading -> {
                    buttonText.isVisible = false
                    progressSpinner.isVisible = true
                    loginCodeField.apply {
                        isVisible = uiState.isCodeSent
                        isEnabled = false
                    }
                    loginButton.isEnabled = false
                    loginEmailField.apply {
                        isEnabled = false
                        setText(uiState.email)
                    }
                }
                is LoggedIn -> {
                    navigation.navigateToLandingPage()
                }
            }
        }
    }
}