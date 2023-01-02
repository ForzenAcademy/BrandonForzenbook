package com.example.forzenbook.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldErrorText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(4.dp),
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
fun BlackButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .height(40.dp)
            .width(200.dp)
            .alpha( if (isEnabled) { 1f } else { 0.5f } )
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HintText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
fun NotificationText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Composable
fun InputInfoTextField(
    hint: String,
    characterLimit: Int = 64,
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
        modifier = Modifier.padding(4.dp),
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
            .padding(4.dp)
            .clickable { onClick() },
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun DimBackgroundLoading() {
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
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
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Composable
fun DimBackgroundNotification(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() },
    ) {
        content()
    }
}

@Composable
fun TitleText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(12.dp),
        text = text,
        style = MaterialTheme.typography.displayMedium,
    )
}