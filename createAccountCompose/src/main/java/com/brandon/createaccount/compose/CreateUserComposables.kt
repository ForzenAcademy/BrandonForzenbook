package com.brandon.createaccount.compose

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import com.brandon.composecore.composables.*
import com.brandon.composecore.constants.ComposableConstants.CREATE_ACCOUNT_TITLE_WEIGHT
import com.brandon.composecore.constants.ComposableConstants.DEFAULT_WEIGHT
import com.brandon.composecore.constants.ComposableConstants.EMAIL_CHAR_LIMIT
import com.brandon.composecore.constants.ComposableConstants.NAME_CHAR_LIMIT
import com.brandon.composecore.constants.ComposableConstants.TEXT_FIELD_MAX_LINES
import com.brandon.composecore.navigation.LocalNavController
import com.brandon.composecore.navigation.NavDestinations
import com.brandon.composecore.theme.dimens
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.Companion.KEYBOARD_ERROR
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.Companion.VIEW_ERROR_TAG
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.CreateAccountUiState
import com.brandon.createaccount.core.viewmodel.CreateAccountViewModel.CreateAccountUiState.*
import com.brandon.uicore.R
import java.util.*

@Composable
fun CreateAccountContent(
    state: CreateAccountUiState,
    onCheckConnection: () -> Boolean,
    onSubmit: (String, String, String, String, String) -> Unit,
) {
    when (state) {
        is Idle -> {
            CreateAccount(state = state, onSubmit = onSubmit, onCheckConnection = onCheckConnection)
        }
        is Loading -> {
            CreateAccountLoading(state = state)
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
    FakeLoginScreen()
    SuccessDialog(
        title = resources.getString(R.string.core_success),
        body = resources.getString(R.string.create_account_success_body),
        buttonText = resources.getString(R.string.create_account_to_login)
    ) {
        navController?.navigate(NavDestinations.LOGIN_INPUT) {
            popUpTo(NavDestinations.LOGIN_INPUT) { inclusive = true }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateAccount(
    state: Idle = Idle(),
    onCheckConnection: () -> Boolean,
    onSubmit: (String, String, String, String, String) -> Unit,
) {
    var firstName by rememberSaveable { mutableStateOf(state.firstName) }
    var lastName by rememberSaveable { mutableStateOf(state.lastName) }
    var dateOfBirth by rememberSaveable { mutableStateOf(state.dateOfBirth) }
    var email by rememberSaveable { mutableStateOf(state.email) }
    var location by rememberSaveable { mutableStateOf(state.location) }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    var fNameError by rememberSaveable { mutableStateOf(false) }
    var lNameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var locationError by rememberSaveable { mutableStateOf(false) }
    var emptyFieldError by rememberSaveable { mutableStateOf(false) }
    var datePickerOpen by rememberSaveable { mutableStateOf(false) }
    val res = LocalContext.current.resources
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = Modifier,
        topBar = { CreateAccountTopBar() },
        content = { padding ->
            YellowColumn(
                modifier = Modifier.padding(padding)
            ) {
                InputInfoTextField(
                    hint = res.getString(R.string.create_account_first_name_hint),
                    currentText = firstName,
                    onTextChange = { firstName = it },
                    onMaxCharacterLength = { fNameError = it },
                    characterLimit = NAME_CHAR_LIMIT
                )
                if (state.isFirstNameError || fNameError) TextFieldErrorText(text = res.getString(R.string.create_account_name_error))
                InputInfoTextField(
                    hint = res.getString(R.string.create_account_last_name_hint),
                    currentText = lastName,
                    onTextChange = { lastName = it },
                    onMaxCharacterLength = { lNameError = it },
                    characterLimit = NAME_CHAR_LIMIT
                )
                if (state.isLastNameError || lNameError) TextFieldErrorText(text = res.getString(R.string.create_account_name_error))
                ReadOnlyDateTextField(
                    hint = res.getString(R.string.create_account_birth_date_format_hint),
                    currentText = dateOfBirth,
                    onTextChange = { dateOfBirth = it },
                    onClickEvent = { datePickerOpen = true }
                )
                if (state.isDateError) TextFieldErrorText(text = res.getString(R.string.create_account_date_error))
                InputInfoTextField(
                    hint = res.getString(R.string.core_email_hint),
                    onTextChange = { email = it },
                    currentText = email,
                    characterLimit = EMAIL_CHAR_LIMIT,
                    onMaxCharacterLength = { emailError = it },
                )
                if (state.isEmailError || emailError) TextFieldErrorText(text = res.getString(R.string.core_email_error))
                if (state.isDuplicateUser) TextFieldErrorText(text = res.getString(R.string.create_account_duplicate_user_error))
                InputInfoTextField(
                    hint = res.getString(R.string.create_account_location_hint),
                    onTextChange = { location = it },
                    currentText = location,
                    onMaxCharacterLength = { locationError = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go)
                )
                if (state.isLocationError || locationError) TextFieldErrorText(
                    text = res.getString(
                        R.string.create_account_location_error
                    )
                )
                BlackButton(text = res.getString(R.string.create_account_create_button_text)) {
                    try {
                        keyboardController?.hide()
                    } catch (_: Exception) {
                        Log.v(VIEW_ERROR_TAG, KEYBOARD_ERROR)
                    }
                    if (onCheckConnection()) {
                        if (firstName.isNotEmpty() && lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && email.isNotEmpty() && location.isNotEmpty()) {
                            if (!fNameError && !lNameError && !emailError && !locationError) {
                                onSubmit(firstName, lastName, dateOfBirth, email, location)
                            }
                        } else emptyFieldError = true
                    } else isDialogOpen = true
                }
                if (emptyFieldError) TextFieldErrorText(text = res.getString(R.string.core_required_fields_error))
            }
        }
    )
    if (isDialogOpen) {
        AlertDialog(
            title = res.getString(R.string.core_error_no_internet_connection),
            body = res.getString(R.string.core_error_connect_and_try_again),
            onDismissRequest = { isDialogOpen = false },
        )
    }
    if (datePickerOpen) {
        LaunchedEffect(Unit) {
            dateDialog(context, onDismiss = { datePickerOpen = false }) {
                dateOfBirth = it
                datePickerOpen = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountLoading(
    state: Loading = Loading(),
) {
    val resources = LocalContext.current.resources
    Scaffold(
        modifier = Modifier,
        topBar = { CreateAccountTopBar() },
        content = { padding ->
            YellowColumn(
                modifier = Modifier.padding(padding)
            ) {
                InputInfoTextField(
                    hint = resources.getString(R.string.create_account_first_name_hint),
                    isEnabled = false,
                    currentText = state.firstName
                )
                InputInfoTextField(
                    hint = resources.getString(R.string.create_account_last_name_hint),
                    isEnabled = false,
                    currentText = state.lastName
                )
                ReadOnlyDateTextField(
                    hint = resources.getString(R.string.create_account_birth_date_format_hint),
                    isEnabled = false,
                    currentText = state.dateOfBirth,
                )
                InputInfoTextField(
                    hint = resources.getString(R.string.core_email_hint),
                    isEnabled = false,
                    currentText = state.email
                )
                InputInfoTextField(
                    hint = resources.getString(R.string.create_account_location_hint),
                    isEnabled = false,
                    currentText = state.location
                )
                LoadingBlackButton()
            }
        }
    )
}

fun dateDialog(
    context: Context,
    onDismiss: () -> Unit,
    onDateChange: (String) -> Unit,
) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        R.style.MySpinnerStyle,
        null,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDateSetListener { _, year, month, dayOfMonth ->
            onDateChange("${month + 1}/$dayOfMonth/$year")
            hide()
        }
        setOnDismissListener { onDismiss() }
    }.show()
}

@Composable
fun CreateAccountTopBar() {
    val navController = LocalNavController.current
    val resources = LocalContext.current.resources
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.tertiary)
            .padding(MaterialTheme.dimens.paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            modifier = Modifier
                .weight(DEFAULT_WEIGHT)
                .clickable {
                    navController?.navigate(NavDestinations.LOGIN_INPUT) {
                        popUpTo(NavDestinations.LOGIN_INPUT) { inclusive = true }
                    }
                },
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = resources.getString(R.string.create_account_back_arrow),
            tint = MaterialTheme.colorScheme.onSecondary,
        )
        Text(
            modifier = Modifier.weight(CREATE_ACCOUNT_TITLE_WEIGHT),
            text = resources.getString(R.string.create_account_title_text),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            maxLines = TEXT_FIELD_MAX_LINES,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(DEFAULT_WEIGHT))
    }
}

@Composable
fun FakeLoginScreen() {
    val resources = LocalContext.current.resources
    YellowColumn {
        ImageTitle(
            imageId = R.drawable.forzenlogo,
            text = resources.getString(R.string.core_login_title),
        )
        InputInfoTextField(hint = resources.getString(R.string.core_email_hint))
        BlackButton(text = resources.getString(R.string.core_get_code_text))
        RedirectText(text = resources.getString(R.string.core_create_account))
    }
}