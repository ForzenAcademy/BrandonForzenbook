package com.brandon.loginlegacy.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import com.brandon.logincore.viewmodel.LoginViewModel
import com.brandon.logincore.viewmodel.LoginViewModel.LoginUiState.*
import com.brandon.loginlegacy.databinding.LoginScreenBinding
import com.brandon.loginlegacy.viewmodel.LegacyLoginViewmodel
import com.brandon.uicore.R
import com.brandon.uicore.connectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginScreenActivity : AppCompatActivity() {

    private val loginViewModel: LegacyLoginViewmodel by viewModels()
    private lateinit var binding: LoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.viewModelScope.launch {
            loginViewModel.uiState.collect {
                updateUi(it)
                Log.e(LoginViewModel.VIEWMODEL_TAG, "${loginViewModel.uiState}")
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
            // TODO FA-101 Navigate to Create Account Page
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

                    if (uiState.isGenericError) connectionDialog(this@LoginScreenActivity)
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
                    // TODO FA-103 Navigate to a Landing Page
                }
            }
        }
    }
}