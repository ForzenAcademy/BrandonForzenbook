package com.brandon.forzenbook.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.forzenbook.R
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState.Done
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel.ResetPasswordState.Idle

@Composable
fun ResetPasswordContent(
    state: ResetPasswordState,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    when (state) {
        is Idle -> {
            FakeLoginScreen()
            ResetPasswordScreen(
                onSubmit = onSubmit,
                onDismiss = onDismiss,
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
fun ResetPasswordScreen(
    isOnCoolDown: Boolean = false,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var emailError by rememberSaveable {
        mutableStateOf(false)
    }
    val resources = LocalContext.current.resources
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxSize(),
    ) {
        YellowWrapColumn {
            NotificationText(text = resources.getString(R.string.resetPasswordEmailTitle))
            InputInfoTextField(
                hint = resources.getString(R.string.resetPasswordEmailHint),
                onTextChange = {
                    email = it
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth(0.75f).wrapContentHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReducedWidthBlackButton(
                    text = resources.getString(R.string.resetPasswordEmailCancel),
                    modifier = Modifier.weight(1f),
                ) {
                    onDismiss()
                }
                ReducedWidthBlackButton(
                    text = resources.getString(R.string.resetPasswordEmailButtonText),
                    modifier = Modifier.weight(1f),
                    isEnabled = isOnCoolDown
                ) {
                    if (email.isNotEmpty()) {
                        onSubmit(email)
                    } else {
                        emailError = true
                    }
                }
            }
            if (emailError) {
                TextFieldErrorText(text = resources.getString(R.string.resetPasswordEmailEmptyError))
            }
        }
    }
}