package com.brandon.forzenbook.view.composables

import android.util.Log
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
import com.brandon.forzenbook.view.MainActivity.Companion.VIEW_LOG_TAG
import com.brandon.forzenbook.view.navigation.LocalNavController
import com.brandon.forzenbook.view.navigation.NavigationDestinations
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.Companion.EMAIL_CHAR_LIMIT
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.LoginUiState
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel.LoginUiState.*
import com.example.forzenbook.R

@Composable
fun LoginContent(
    state: LoginUiState,
    onSubmit: (String, String) -> Unit
) {
    when (state) {
        is PreCode -> {
            // TODO FA-81 redesign UI for New States
            GenericErrorDialog("Test") {
            }
//            LoginScreen(onSubmit = onSubmit)
        }
        is CodeSent -> {
            // TODO this is where I show the code field FA-81
        }
        is Error -> {
            LoginScreen(state = state, onSubmit = onSubmit)
        }
        is Loading -> {
            FakeLoginScreen()
            DimBackgroundLoading()
        }
        is LoginWithCode -> {
            // TODO navigate to next app screen FA-81
            Log.e(VIEW_LOG_TAG, "Login Success")
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
    var code by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }
    var codeError by rememberSaveable {
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
            characterLimit = EMAIL_CHAR_LIMIT,
            onMaxCharacterLength = {
                emailError = it
            },
        )
        if (emailError) {
            TextFieldErrorText(text = resources.getString(R.string.loginCharLengthLimit))
        }
        if (state.isEmailError) {
            TextFieldErrorText(text = resources.getString(R.string.loginUserNameError))
        }
        InputInfoTextField(
            hint = resources.getString(R.string.logincodeHint),
            onTextChange = {
                code = it
            },
            onMaxCharacterLength = {
                codeError = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go)
        )
        if (codeError) {
            TextFieldErrorText(text = resources.getString(R.string.loginCharLengthLimit))
        }
        BlackButton(text = resources.getString(R.string.loginButtonText)) {
            keyboardController?.hide()
            if (username.isNotEmpty() && code.isNotEmpty()) {
                onSubmit(username, code)
            } else {
                emptyFieldError = true
            }
        }
        if (emptyFieldError) {
            TextFieldErrorText(text = resources.getString(R.string.requiredFieldsError))
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