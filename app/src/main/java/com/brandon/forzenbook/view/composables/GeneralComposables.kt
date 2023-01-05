package com.brandon.forzenbook.view.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.brandon.forzenbook.view.composables.ComposableConstants.BUTTON_DEFAULT_ENABLED
import com.brandon.forzenbook.view.composables.ComposableConstants.DEFAULT_WEIGHT
import com.brandon.forzenbook.view.composables.ComposableConstants.DISABLED_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.ENABLED_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.NO_ALPHA
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_DEFAULT_ENABLED
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_DEFAULT_INPUT_LIMIT
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_DEFAULT_TEXT
import com.brandon.forzenbook.view.composables.ComposableConstants.TEXT_FIELD_MAX_LINES
import com.brandon.forzenbook.view.theme.LocalDimens
import com.example.forzenbook.R

@Composable
fun TextFieldErrorText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingSmall),
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun YellowColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
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
    isEnabled: Boolean = BUTTON_DEFAULT_ENABLED,
    onClick: () -> Unit = {},
) {
    val dimens = LocalDimens.current
    Box(
        modifier = Modifier
            .padding(
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding,
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding
            )
            .height(dimens.minimumTouchTarget)
            .fillMaxWidth()
            .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { if (isEnabled) onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            maxLines = TEXT_FIELD_MAX_LINES,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun LoadingBlackButton() {
    val dimens = LocalDimens.current
    Box(
        modifier = Modifier
            .padding(
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding,
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding
            )
            .height(dimens.minimumTouchTarget)
            .fillMaxWidth()
            .alpha(DISABLED_ALPHA)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .clip(MaterialTheme.shapes.large),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HintText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingSmall),
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onTertiary,
    )
}

@Composable
fun InputInfoTextField(
    modifier: Modifier = Modifier,
    hint: String,
    currentText: String = TEXT_FIELD_DEFAULT_TEXT,
    characterLimit: Int = TEXT_FIELD_DEFAULT_INPUT_LIMIT,
    isEnabled: Boolean = TEXT_FIELD_DEFAULT_ENABLED,
    onTextChange: (String) -> Unit = {},
    onMaxCharacterLength: (Boolean) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
) {
    val dimens = LocalDimens.current
    TextField(
        value = currentText,
        modifier = modifier
            .padding(
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding,
                dimens.paddingLarge,
                dimens.minimumTouchTargetPadding
            )
            .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA)
            .fillMaxWidth()
            .imePadding(),
        onValueChange = {
            if (currentText.length == characterLimit) {
                onMaxCharacterLength(true)
            } else {
                onTextChange(it)
                onMaxCharacterLength(false)
            }
        },
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.labelMedium,
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
fun ReadOnlyDateTextField(
    modifier: Modifier = Modifier,
    hint: String,
    currentText: String = TEXT_FIELD_DEFAULT_TEXT,
    characterLimit: Int = TEXT_FIELD_DEFAULT_INPUT_LIMIT,
    isEnabled: Boolean = TEXT_FIELD_DEFAULT_ENABLED,
    onTextChange: (String) -> Unit = {},
    onMaxCharacterLength: (Boolean) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    onClickEvent: () -> Unit = {},
) {
    Box {
        val dimens = LocalDimens.current
        TextField(
            value = currentText,
            modifier = modifier
                .padding(
                    dimens.paddingLarge,
                    dimens.minimumTouchTargetPadding,
                    dimens.paddingLarge,
                    dimens.minimumTouchTargetPadding
                )
                .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA)
                .fillMaxWidth()
                .imePadding(),
            onValueChange = {
                if (currentText.length == characterLimit) {
                    onMaxCharacterLength(true)
                } else {
                    onTextChange(it)
                    onMaxCharacterLength(false)
                }
            },
            enabled = isEnabled,
            textStyle = MaterialTheme.typography.labelMedium,
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
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(NO_ALPHA)
                .clickable { onClickEvent() }
        )
    }
}

@Composable
fun RedirectText(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .clickable { if (isEnabled) onClick() }
            .padding(LocalDimens.current.minimumTouchTargetPadding)
            .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA),
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun ImageTitle(
    @DrawableRes imageId: Int,
    text: String,
) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = LocalContext.current.getString(R.string.login_image_description),
        modifier = Modifier.size(LocalDimens.current.largeIcon)
    )
    TitleText(
        text = text
    )
}

@Composable
fun TitleText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(LocalDimens.current.paddingSmall),
        text = text,
        style = MaterialTheme.typography.displayMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = TEXT_FIELD_MAX_LINES,
    )
}

@Composable
fun AlertDialog(
    title: String,
    body: String,
    onDismissRequest: () -> Unit,
) {
    val dimens = LocalDimens.current
    val resources = LocalContext.current.resources
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .height(dimens.notificationDialogHeight)
                .width(dimens.notificationDialogWidth)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall,
                maxLines = TEXT_FIELD_MAX_LINES,
            )
            Text(
                text = body,
                modifier = Modifier
                    .padding(dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
            )
            Row {
                Spacer(modifier = Modifier.weight(DEFAULT_WEIGHT))
                Text(
                    text = resources.getString(R.string.error_dismiss),
                    modifier = Modifier
                        .padding(dimens.minimumTouchTargetPadding)
                        .weight(DEFAULT_WEIGHT)
                        .clickable { onDismissRequest() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = TEXT_FIELD_MAX_LINES,
                )
            }
        }
    }
}

@Composable
fun SuccessDialog(
    title: String,
    body: String,
    buttonText: String,
    onDismissRequest: () -> Unit,
) {
    val dimens = LocalDimens.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .height(dimens.notificationDialogHeight)
                .width(dimens.notificationDialogWidth)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall,
                maxLines = TEXT_FIELD_MAX_LINES,
            )
            Text(
                text = body,
                modifier = Modifier
                    .padding(dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
            )
            Row {
                Spacer(modifier = Modifier.weight(DEFAULT_WEIGHT))
                Text(
                    text = buttonText,
                    modifier = Modifier
                        .padding(dimens.minimumTouchTargetPadding)
                        .weight(DEFAULT_WEIGHT)
                        .clickable { onDismissRequest() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Magenta,
                    textAlign = TextAlign.Center,
                    maxLines = TEXT_FIELD_MAX_LINES,
                )
            }
        }
    }
}