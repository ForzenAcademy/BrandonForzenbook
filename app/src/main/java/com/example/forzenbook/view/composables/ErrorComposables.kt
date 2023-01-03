package com.example.forzenbook.view.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.forzenbook.R
import com.example.forzenbook.viewmodels.ForzenTopLevelViewModel
import com.example.forzenbook.viewmodels.ManageAccountViewModel

@Composable
fun InternetIssue(
    onDismiss: () -> Unit
) {
    val resources = LocalContext.current.resources
    DimBackgroundNotification(onDismiss = onDismiss) {
        YellowWrapColumn {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            NotificationText(text = resources.getString(R.string.errorNoInternetConnection))
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
        }
    }
}

@Composable
fun ServiceIssue(
    onDismiss: () -> Unit
) {
    val resources = LocalContext.current.resources
    DimBackgroundNotification(onDismiss = onDismiss) {
        YellowWrapColumn {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            NotificationText(text = resources.getString(R.string.errorNoServiceConnection))
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
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

@Composable
fun FakeCreateAccountScreen() {
    val resources = LocalContext.current.resources
    YellowColumn {
        TitleText(text = resources.getString(R.string.createAccountTitleText))
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountFirstNameHint),
            onTextChange = { },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountLastNameHint),
            onTextChange = { },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountPasswordHint),
            onTextChange = { },
            characterLimit = ForzenTopLevelViewModel.PASSWORD_CHAR_LIMIT,
            onMaxCharacterLength = { },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountBirthDateFormatHint),
            onTextChange = { },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountEmailHint),
            onTextChange = { },
            characterLimit = ManageAccountViewModel.EMAIL_CHAR_LIMIT,
            onMaxCharacterLength = { },
        )
        InputInfoTextField(
            hint = resources.getString(R.string.createAccountLocationHint),
            onTextChange = { },
            characterLimit = ManageAccountViewModel.LOCATION_CHAR_LIMIT,
            onMaxCharacterLength = { },
        )
        BlackButton(
            text = resources.getString(R.string.createAccountCreateButtonText),
        ) {
        }
    }
}