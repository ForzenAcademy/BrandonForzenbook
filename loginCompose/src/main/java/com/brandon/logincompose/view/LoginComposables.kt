package com.brandon.logincompose.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.brandon.composecore.composables.*
import com.brandon.composecore.constants.ComposableConstants.EMAIL_CHAR_LIMIT
import com.brandon.composecore.navigation.LocalNavController
import com.brandon.composecore.navigation.NavDestinations
import com.brandon.uicore.R as uiR
import com.brandon.logincompose.viewmodel.LoginViewModel.LoginUiState
import com.brandon.logincompose.viewmodel.LoginViewModel.LoginUiState.*
import com.example.logincompose.R

@Composable
fun LoginContent(
    state: LoginUiState,
    onCheckConnection: () -> Boolean,
    onGetCode: (String) -> Unit,
    onLogin: (String, String) -> Unit,
    onLoggedIn: () -> Unit,
) {
    when (state) {
        is Idle -> {
            LoginScreen(
                state = state,
                onGetCode = onGetCode,
                onLogin = onLogin,
                onCheckConnection = onCheckConnection,
            )
        }
        is Loading -> {
            LoadingLoginScreen(state = state)
        }
        is LoggedIn -> {
            onLoggedIn()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    state: Idle = Idle(),
    onCheckConnection: () -> Boolean,
    onGetCode: (String) -> Unit,
    onLogin: (String, String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf(state.email) }
    var code by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var emptyFieldError by rememberSaveable { mutableStateOf(false) }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val navController = LocalNavController.current
    val resources = LocalContext.current.resources
    val keyboardController = LocalSoftwareKeyboardController.current
    YellowColumn {
        ImageTitle(
            imageId = uiR.drawable.forzenlogo,
            text = resources.getString(uiR.string.core_login_title),
        )
        InputInfoTextField(
            hint = resources.getString(uiR.string.core_email_hint),
            currentText = email,
            onTextChange = { email = it },
            characterLimit = EMAIL_CHAR_LIMIT,
            onMaxCharacterLength = { emailError = it },
        )
        if (emailError) TextFieldErrorText(text = resources.getString(uiR.string.core_char_length_limit))
        if (state.isEmailError) TextFieldErrorText(text = resources.getString(uiR.string.core_email_error))
        if (state.isCodeSent) {
            var codeError by rememberSaveable { mutableStateOf(false) }
            InputInfoTextField(
                hint = resources.getString(R.string.login_code_hint),
                onTextChange = { code = it },
                currentText = code,
                onMaxCharacterLength = { codeError = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Number
                )
            )
            if (codeError) TextFieldErrorText(text = resources.getString(uiR.string.core_char_length_limit))
        }
        BlackButton(
            text = if (state.isCodeSent) resources.getString(R.string.login_button_text) else resources.getString(
                uiR.string.core_get_code_text
            )
        ) {
            keyboardController?.hide()
            emptyFieldError = false
            if (onCheckConnection()) {
                if (state.isCodeSent) {
                    if (email.isNotEmpty() && code.isNotEmpty()) onLogin(email, code)
                    else emptyFieldError = true
                } else {
                    if (email.isNotEmpty()) onGetCode(email)
                    else emptyFieldError = true
                }
            } else isDialogOpen = true
        }
        if (emptyFieldError) TextFieldErrorText(text = resources.getString(uiR.string.core_required_fields_error))
        RedirectText(text = resources.getString(uiR.string.core_create_account)) {
            navController?.navigate(NavDestinations.CREATE_ACCOUNT) {
                popUpTo(NavDestinations.CREATE_ACCOUNT) { inclusive = true }
            }
        }
    }
    if (isDialogOpen) {
        AlertDialog(
            title = resources.getString(uiR.string.core_error_no_internet_connection),
            body = resources.getString(uiR.string.core_error_connect_and_try_again),
            onDismissRequest = { isDialogOpen = false },
        )
    }
}

@Composable
fun LoadingLoginScreen(
    state: Loading
) {
    val resources = LocalContext.current.resources
    YellowColumn {
        ImageTitle(
            imageId = uiR.drawable.forzenlogo,
            text = resources.getString(uiR.string.core_login_title),
        )
        InputInfoTextField(
            hint = resources.getString(uiR.string.core_email_hint),
            currentText = state.email,
            isEnabled = false,
        )
        if (state.isCodeSent) {
            InputInfoTextField(
                hint = resources.getString(R.string.login_code_hint),
                isEnabled = false,
            )
        }
        LoadingBlackButton()
        RedirectText(text = resources.getString(uiR.string.core_create_account))
    }
}