package com.example.forzenbook.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.forzenbook.R
import com.example.forzenbook.viewmodels.ForzenTopLevelViewModel
import com.example.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState
import com.example.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState.Done
import com.example.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState.Idle

@Composable
fun ResetPasswordContent(
    state: ResetPasswordState,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    when (state) {
        is Idle -> {
            FakeLoginScreen()
            ResetPasswordNotification(
                onDismiss = onDismiss,
                onSubmit = onSubmit,
                isOnCoolDown = state.isOnCoolDown
            )
        }
        is Done -> {
            FakeLoginScreen()
            EmailSentNotification(onDismiss = onDismiss)
        }
    }
}

@Composable
fun ResetPasswordNotification(
    isOnCoolDown: Boolean = false,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    var email by remember {
        mutableStateOf("")
    }
    val resources = LocalContext.current.resources
    DimBackgroundNotification(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.tertiary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NotificationText(text = resources.getString(R.string.resetPasswordEmailTitle))
            InputInfoTextField(
                hint = resources.getString(R.string.resetPasswordEmailHint),
                onTextChange = {
                    email = it
                },
            )
            BlackButton(
                text = resources.getString(R.string.resetPasswordEmailButtonText),
                isEnabled = isOnCoolDown
            ) {
                onSubmit(email)
            }
        }
    }
}

@Composable
fun EmailSentNotification(
    onDismiss: () -> Unit
) {
    val resources = LocalContext.current.resources
    DimBackgroundNotification(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NotificationText(text = resources.getString(R.string.resetPasswordNotification))
            BlackButton(text = resources.getString(R.string.resetPasswordEmailButtonDismissText)) {
                onDismiss()
            }
        }
    }
}

@Composable
fun FakeLoginScreen(
) {
    val resources = LocalContext.current.resources
    YellowColumn {
        LoginTitle()
        InputInfoTextField(
            hint = resources.getString(R.string.loginUserNameHint),
            onTextChange = {},
            characterLimit = ForzenTopLevelViewModel.USERNAME_CHAR_LIMIT,
            onMaxCharacterLength = {},
        )
        InputInfoTextField(
            hint = resources.getString(R.string.loginPasswordHint),
            onTextChange = {
            },
            characterLimit = ForzenTopLevelViewModel.PASSWORD_CHAR_LIMIT,
            onMaxCharacterLength = {
            },
        )
        BlackButton(text = resources.getString(R.string.loginButtonText)) {}
        RedirectText(text = resources.getString(R.string.loginResetPassword)) {}
        RedirectText(text = resources.getString(R.string.loginCreateAccount)) {}
    }
}