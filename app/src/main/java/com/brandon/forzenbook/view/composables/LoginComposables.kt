package com.brandon.forzenbook.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.forzenbook.R
import com.brandon.forzenbook.view.navigation.LocalNavController
import com.brandon.forzenbook.view.navigation.NavigationDestinations
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.Companion.PASSWORD_CHAR_LIMIT
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.Companion.USERNAME_CHAR_LIMIT
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.LoginUiState
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.LoginUiState.*

@Composable
fun LoginContent(
    state: LoginUiState,
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    when (state) {
        is Idle -> {
            LoginScreen(onSubmit = onSubmit)
        }
        is Error -> {
            if (state.isServiceError) {
                FakeLoginScreen()
                ServiceIssue(onDismiss = onDismiss)
            } else if (state.isNetworkError) {
                FakeLoginScreen()
                InternetIssue(onDismiss = onDismiss)
            } else {
                LoginScreen(state = state, onSubmit = onSubmit)
            }
        }
        is Loading -> {
            FakeLoginScreen()
            DimBackgroundLoading()
        }
        is Loaded -> {
            println("Brandon_Test_View Login Success")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    state: Error = Error(),
    onSubmit: (String, String) -> Unit,
) {
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var nameError by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordError by rememberSaveable {
        mutableStateOf(false)
    }
    var emptyFieldError by rememberSaveable {
        mutableStateOf(false)
    }
    val navController = LocalNavController.current
    val resources = LocalContext.current.resources
    val keyboardController = LocalSoftwareKeyboardController.current
    YellowColumn {
        LoginTitle()
        InputInfoTextField(
            hint = resources.getString(R.string.loginUserNameHint),
            onTextChange = {
                username = it
            },
            characterLimit = USERNAME_CHAR_LIMIT,
            onMaxCharacterLength = {
                nameError = it
            },
        )
        if (nameError) {
            TextFieldErrorText(text = resources.getString(R.string.loginCharLengthLimit))
        }
        if (state.isUserNameError) {
            TextFieldErrorText(text = resources.getString(R.string.loginUserNameError))
        }
        InputInfoTextField(
            hint = resources.getString(R.string.loginPasswordHint),
            onTextChange = {
                password = it
            },
            characterLimit = PASSWORD_CHAR_LIMIT,
            onMaxCharacterLength = {
                passwordError = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go)
        )
        if (state.isPasswordError) {
            TextFieldErrorText(text = resources.getString(R.string.loginPasswordError))
        }
        if (passwordError) {
            TextFieldErrorText(text = resources.getString(R.string.loginCharLengthLimit))
        }
        if (state.isInvalidCredentialsError) {
            TextFieldErrorText(text = resources.getString(R.string.loginInvalidCredentials))
        }
        BlackButton(text = resources.getString(R.string.loginButtonText)) {
            keyboardController?.hide()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                onSubmit(username, password)
            } else {
                emptyFieldError = true
            }
        }
        if (emptyFieldError) {
            TextFieldErrorText(text = resources.getString(R.string.requiredFieldsError))
        }
        RedirectText(text = resources.getString(R.string.loginResetPassword)) {
            navController?.navigate(NavigationDestinations.FORGOT_PASSWORD) {
                popUpTo(NavigationDestinations.FORGOT_PASSWORD) { inclusive = true }
            }
        }
        RedirectText(text = resources.getString(R.string.loginCreateAccount)) {
            navController?.navigate(NavigationDestinations.CREATE_ACCOUNT) {
                popUpTo(NavigationDestinations.CREATE_ACCOUNT) { inclusive = true }
            }
        }
    }
}

@Composable
fun LoginTitle() {
    Image(
        painter = painterResource(id = R.drawable.forzenlogo),
        contentDescription = LocalContext.current.getString(R.string.loginImageDescription),
        modifier = Modifier.size(250.dp)
    )
    TitleText(
        text = LocalContext.current.getString(R.string.loginTitle)
    )
}