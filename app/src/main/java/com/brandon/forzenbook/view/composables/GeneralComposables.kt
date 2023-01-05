package com.brandon.forzenbook.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.brandon.forzenbook.view.composables.ComposableConstants.BUTTON_DISABLED_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.BUTTON_ENABLED_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.LOADING_BACKGROUND_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_INPUT_LIMIT
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_MAX_LINES
import com.brandon.forzenbook.view.theme.LocalDimens

@Composable
fun TextFieldErrorText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingSmall),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun YellowColumn(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content()
    }
}

@Composable
fun YellowWrapColumn(
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.large
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content()
    }
}

@Composable
fun BlackButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(LocalDimens.current.paddingSmall)
            .height(LocalDimens.current.buttonHeight)
            .fillMaxWidth()
            .alpha(if (isEnabled) BUTTON_ENABLED_ALPHA else BUTTON_DISABLED_ALPHA)
            .background(
                color = Color.Black,
                shape = MaterialTheme.shapes.large,
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            maxLines = TEXT_FIELD_MAX_LINES,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HintText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingSmall),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
fun InputInfoTextField(
    hint: String,
    characterLimit: Int = TEXT_FIELD_INPUT_LIMIT,
    onTextChange: (String) -> Unit,
    onMaxCharacterLength: (Boolean) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    var currentText by rememberSaveable {
        mutableStateOf("")
    }
    TextField(
        value = currentText,
        modifier = Modifier
            .padding(LocalDimens.current.paddingSmall)
            .fillMaxWidth()
            .imePadding(),
        onValueChange = {
            if (currentText.length == characterLimit) {
                onMaxCharacterLength(true)
            } else {
                currentText = it
                onTextChange(it)
                onMaxCharacterLength(false)
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        placeholder = {
            HintText(text = hint)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    )
}

@Composable
fun RedirectText(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .padding(LocalDimens.current.paddingSmall)
            .clickable { onClick() },
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun DimBackgroundLoading() {
    Surface(
        color = Color.Black.copy(alpha = LOADING_BACKGROUND_ALPHA),
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(LocalDimens.current.circularLoadingIndicator)
            )
        }
    }
}

@Composable
fun TitleText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingLarge),
        text = text,
        style = MaterialTheme.typography.displayMedium,
    )
}