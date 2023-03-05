package com.brandon.loginfragments.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.brandon.fragmentcore.NotificationDialogFragment
import com.brandon.logincore.viewmodel.LoginViewModel
import com.brandon.loginfragments.databinding.LoginScreenBinding
import com.brandon.loginfragments.viewmodel.FragmentLoginViewmodel
import com.brandon.uicore.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: FragmentLoginViewmodel by activityViewModels()

    private lateinit var binding: LoginScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            // TODO FA-125 Add Navigation Between Fragments
        }

        loginViewModel.viewModelScope.launch {
            loginViewModel.uiState.collect {
                updateUi(it)
                Log.e(LoginViewModel.VIEWMODEL_TAG, "${loginViewModel.uiState}")
            }
        }
    }

    private fun updateUi(uiState: LoginViewModel.LoginUiState) {
        binding.apply {
            when (uiState) {
                is LoginViewModel.LoginUiState.Idle -> {
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

                    if (uiState.isGenericError) {
                        NotificationDialogFragment(
                            title = getString(R.string.core_error_no_internet_connection),
                            body = getString(R.string.core_error_connect_and_try_again),
                        ).show(childFragmentManager, null)
                    }
                }
                is LoginViewModel.LoginUiState.Loading -> {
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
                is LoginViewModel.LoginUiState.LoggedIn -> {
                    // TODO FA-125 Add Navigation Between Fragments
                }
            }
        }
    }
}