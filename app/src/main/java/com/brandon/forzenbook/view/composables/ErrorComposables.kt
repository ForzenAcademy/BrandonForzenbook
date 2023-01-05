package com.brandon.forzenbook.view.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.brandon.forzenbook.viewmodels.ForzenTopLevelViewModel
import com.brandon.forzenbook.viewmodels.ManageAccountViewModel
import com.example.forzenbook.R

@Composable
fun GenericErrorDialog(
    titleErrorText: String,
    onDismiss: () -> Unit
) {
    // TODO FA-81 redesign UI for New States
    Dialog(onDismissRequest = onDismiss) {
        YellowWrapColumn {
            TitleText(text = titleErrorText)
            DialogBodyText(text = """
                This is a long winded test body of text intended to test
                my dialog text area. I want this to be able to contain 3 lines at
                most
            """.trimIndent())
        }
    }
}

@Composable
fun InternetErrorDialog(
    titleErrorText: String,
    onDismiss: () -> Unit
) {
    // TODO FA-81 redesign UI for New States
    Dialog(onDismissRequest = onDismiss) {
        YellowWrapColumn {
            TitleText(text = titleErrorText)
        }
    }
}

@Composable
fun DialogBodyText(
    text: String
) {
    // TODO FA-81 redesign UI for New States
    Text(text = text)
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
            characterLimit = ForzenTopLevelViewModel.EMAIL_CHAR_LIMIT,
            onMaxCharacterLength = {},
        )
        InputInfoTextField(
            hint = resources.getString(R.string.logincodeHint),
            onTextChange = {
            },
            characterLimit = ForzenTopLevelViewModel.CODE_CHAR_LIMIT,
            onMaxCharacterLength = {
            },
        )
        BlackButton(text = resources.getString(R.string.loginButtonText)) {}
        RedirectText(text = resources.getString(R.string.loginResetcode)) {}
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
            hint = resources.getString(R.string.createAccountcodeHint),
            onTextChange = { },
            characterLimit = ForzenTopLevelViewModel.CODE_CHAR_LIMIT,
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