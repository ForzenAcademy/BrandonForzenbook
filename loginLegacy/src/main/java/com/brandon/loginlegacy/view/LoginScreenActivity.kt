package com.brandon.loginlegacy.view

import android.os.Bundle
import android.text.InputType
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.brandon.logincore.viewmodel.LoginViewModel.LoginUiState.*
import com.brandon.loginlegacy.databinding.LoginScreenBinding
import com.brandon.loginlegacy.viewmodel.LegacyLoginViewmodel
import com.brandon.utilities.disable
import com.brandon.utilities.enable
import com.brandon.utilities.toEditable
import com.brandon.uicore.R as uiR

class LoginScreenActivity : AppCompatActivity() {

    private val loginViewModel: LegacyLoginViewmodel by viewModels()
    private lateinit var binding: LoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val emailField = binding.loginEmailField
        val emailError = binding.loginEmailError
        val codeField = binding.loginCodeField
        val fieldsError = binding.loginRequiredFieldsError
        val buttonSpinner = binding.progressSpinner
        val buttonText = binding.buttonText
        val loginButton = binding.loginButton

        binding.loginButton.setOnClickListener {
            fieldsError.visibility = GONE
            val email = binding.loginEmailField.text.toString()
            val code = binding.loginCodeField.text.toString()
            if (email.isNotEmpty()) {
                if (code.isNotEmpty()) loginViewModel.loginClicked(email, code)
                else loginViewModel.loginClicked(email)
            } else fieldsError.visibility = VISIBLE
        }

        binding.createAccountRedirect.setOnClickListener {
            // TODO FA-101 Navigate to Create Account Page
        }

        loginViewModel.onUpdate = {
            when (it) {
                is Idle -> {
                    buttonSpinner.visibility = GONE
                    buttonText.visibility = VISIBLE
                    codeField.visibility = if (it.isCodeSent) VISIBLE else GONE
                    emailError.visibility = if (it.isEmailError) VISIBLE else GONE

                    loginButton.isEnabled = true
                    emailField.enable(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    codeField.enable(InputType.TYPE_CLASS_NUMBER)

                    emailField.text = it.email.toEditable()
                    buttonText.text =
                        if (it.isCodeSent) getString(uiR.string.core_get_code_text)
                        else getString(uiR.string.login_button_text)
                }
                is Loading -> {
                    buttonText.visibility = GONE
                    buttonSpinner.visibility = VISIBLE
                    codeField.visibility = if (it.isCodeSent) VISIBLE else GONE

                    loginButton.isEnabled = false
                    emailField.disable()
                    codeField.disable()

                    emailField.text = it.email.toEditable()
                }
                is LoggedIn -> {
                    // TODO FA-103 Navigate to a Landing Page
                }
            }
        }
        // TODO
        // Dialog for connection error
    }
}