package com.brandon.forzenbook.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.brandon.forzenbook.view.navigation.LocalNavController
import com.brandon.forzenbook.view.navigation.NavigationDestinations
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.Companion.EMAIL_CHAR_LIMIT
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.Companion.LOCATION_CHAR_LIMIT
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.CreateAccountUiState
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.CreateAccountUiState.*
import com.example.forzenbook.R

@Composable
fun CreateAccountContent(
    state: CreateAccountUiState,
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, String, String) -> Unit,
) {
    when (state) {
        is Idle -> {
            CreateAccount(onSubmit = onSubmit)
        }
        is Error -> {
            if (state.isNetworkError) {
                FakeCreateAccountScreen()
                InternetIssue(onDismiss = onDismiss)
            } else {
                CreateAccount(state = state, onSubmit = onSubmit)
            }
        }
        is Loading -> {
            FakeCreateAccountScreen()
            DimBackgroundLoading()
        }
        is Loaded -> {
            CreateSuccess()
        }
    }
}

@Composable
fun CreateSuccess() {
    val navController = LocalNavController.current
    val resources = LocalContext.current.resources
    YellowColumn {
        TitleText(text = resources.getString(R.string.createAccountSuccess))
        BlackButton(text = resources.getString(R.string.createAccountToLogin)) {
            navController?.navigate(NavigationDestinations.LOGIN_INPUT) {
                popUpTo(NavigationDestinations.LOGIN_INPUT) { inclusive = true }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateAccount(
    state: Error = Error(),
    onSubmit: (String, String, String, String, String) -> Unit
) {
    var firstName by rememberSaveable {
        mutableStateOf("")
    }
    var lastName by rememberSaveable {
        mutableStateOf("")
    }
    var code by rememberSaveable {
        mutableStateOf("")
    }
    var dateOfBirth by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var location by rememberSaveable {
        mutableStateOf("")
    }
    var codeError by rememberSaveable {
        mutableStateOf(false)
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }
    var locationError by rememberSaveable {
        mutableStateOf(false)
    }
    var emptyFieldError by rememberSaveable {
        mutableStateOf(false)
    }
    val resources = LocalContext.current.resources
    val keyboardController = LocalSoftwareKeyboardController.current
    YellowColumn {
        TitleText(
            text = resources.getString(R.string.createAccountTitleText)
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountFirstNameHint),
            onTextChange = { firstName = it },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountLastNameHint),
            onTextChange = { lastName = it },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountBirthDateFormatHint),
            onTextChange = { dateOfBirth = it },
            visualTransformation = ManageAccountViewModel.DateTransformation()
        )
        if (state.isDateError) {
            TextFieldErrorText(text = resources.getString(R.string.createAccountDateError))
        }
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountEmailHint),
            onTextChange = { email = it },
            characterLimit = EMAIL_CHAR_LIMIT,
            onMaxCharacterLength = { emailError = it },
        )
        if (state.isEmailError) {
            TextFieldErrorText(text = resources.getString(R.string.createAccountEmailError))
        }
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountLocationHint),
            onTextChange = { location = it },
            characterLimit = LOCATION_CHAR_LIMIT,
            onMaxCharacterLength = { locationError = it },
        )
        if (state.isLocationError) {
            TextFieldErrorText(text = resources.getString(R.string.createAccountLocationError))
        }
        BlackButton(
            text = resources.getString(R.string.createAccountCreateButtonText),
        ) {
            keyboardController?.hide()
            if (firstName.isNotEmpty() && lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && email.isNotEmpty() && location.isNotEmpty()) {
                onSubmit(firstName, lastName, dateOfBirth, email, location)
            } else {
                emptyFieldError = true
            }
        }
        if (emptyFieldError) {
            TextFieldErrorText(text = resources.getString(R.string.requiredFieldsError))
        }
    }
}