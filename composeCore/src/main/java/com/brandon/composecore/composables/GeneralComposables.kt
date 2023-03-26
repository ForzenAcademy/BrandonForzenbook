package com.brandon.composecore.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
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
import com.brandon.composecore.constants.ComposableConstants.BUTTON_DEFAULT_ENABLED
import com.brandon.composecore.constants.ComposableConstants.DEFAULT_WEIGHT
import com.brandon.composecore.constants.ComposableConstants.DISABLED_ALPHA
import com.brandon.composecore.constants.ComposableConstants.ENABLED_ALPHA
import com.brandon.composecore.constants.ComposableConstants.NO_ALPHA
import com.brandon.composecore.constants.ComposableConstants.TEXT_FIELD_DEFAULT_ENABLED
import com.brandon.composecore.constants.ComposableConstants.TEXT_FIELD_DEFAULT_INPUT_LIMIT
import com.brandon.composecore.constants.ComposableConstants.TEXT_FIELD_DEFAULT_TEXT
import com.brandon.composecore.constants.ComposableConstants.TEXT_FIELD_MAX_LINES
import com.brandon.composecore.theme.dimens
import com.example.composecore.R

@Composable
fun TextFieldErrorText(
    text: String,
) {
    Text(
        modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.error,
    )
}

@Composable
fun YellowColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
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
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = BUTTON_DEFAULT_ENABLED,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding,
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding
            )
            .height(MaterialTheme.dimens.minimumTouchTarget)
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
fun SmallBlackButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = BUTTON_DEFAULT_ENABLED,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(MaterialTheme.dimens.paddingSmall,)
            .height(MaterialTheme.dimens.minimumTouchTarget)
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
    Box(
        modifier = Modifier
            .padding(
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding,
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding
            )
            .height(MaterialTheme.dimens.minimumTouchTarget)
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
        modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onTertiary,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
    TextField(
        value = currentText,
        modifier = modifier
            .padding(
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding,
                MaterialTheme.dimens.paddingLarge,
                MaterialTheme.dimens.minimumTouchTargetPadding
            )
            .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA)
            .fillMaxWidth()
            .imePadding(),
        onValueChange = {
            if (it.length >= characterLimit) {
                onMaxCharacterLength(true)
            } else {
                onMaxCharacterLength(false)
            }
            onTextChange(it)
        },
        enabled = isEnabled,
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true,
        placeholder = { HintText(text = hint) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        TextField(
            value = currentText,
            modifier = modifier
                .padding(
                    MaterialTheme.dimens.paddingLarge,
                    MaterialTheme.dimens.minimumTouchTargetPadding,
                    MaterialTheme.dimens.paddingLarge,
                    MaterialTheme.dimens.minimumTouchTargetPadding
                )
                .alpha(if (isEnabled) ENABLED_ALPHA else DISABLED_ALPHA)
                .fillMaxWidth()
                .imePadding(),
            onValueChange = {
                if (currentText.length == characterLimit) onMaxCharacterLength(true)
                else {
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
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
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
            .padding(MaterialTheme.dimens.minimumTouchTargetPadding)
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
        contentDescription = LocalContext.current.getString(R.string.default_image_description),
        modifier = Modifier.size(MaterialTheme.dimens.largeIcon)
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
        modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
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
    val resources = LocalContext.current.resources
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .height(MaterialTheme.dimens.notificationDialogHeight)
                .width(MaterialTheme.dimens.notificationDialogWidth)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall,
                maxLines = TEXT_FIELD_MAX_LINES,
            )
            Text(
                text = body,
                modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
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
                        .padding(MaterialTheme.dimens.minimumTouchTargetPadding)
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
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .height(MaterialTheme.dimens.notificationDialogHeight)
                .width(MaterialTheme.dimens.notificationDialogWidth)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall,
                maxLines = TEXT_FIELD_MAX_LINES,
            )
            Text(
                text = body,
                modifier = Modifier.padding(MaterialTheme.dimens.paddingSmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
            )
            Row(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.paddingSmall)
                    .height(MaterialTheme.dimens.minimumTouchTarget)
                    .clickable { onDismissRequest() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buttonText,
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.minimumTouchTargetPadding)
                        .weight(DEFAULT_WEIGHT),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Magenta,
                    textAlign = TextAlign.Center,
                    maxLines = TEXT_FIELD_MAX_LINES,
                )
            }
        }
    }
}