package com.brandon.feedcompose

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.brandon.composecore.composables.BlackButton
import com.brandon.composecore.composables.SmallBlackButton
import com.brandon.composecore.composables.YellowColumn
import com.brandon.composecore.constants.ComposableConstants.IMAGE_NO_DESCRIPTION
import com.brandon.composecore.constants.ComposableConstants.POST_FIELD_TEXT_LIMIT
import com.brandon.composecore.theme.dimens
import com.brandon.uicore.R

@Composable
fun FeedScreen(
    onCreateFeedClicked: () -> Unit
) {
    CreatePostRedirect(onCreateFeedClicked = onCreateFeedClicked)
}

@Composable
fun CreatePostRedirect(
    onCreateFeedClicked: () -> Unit
) {
    BlackButton(text = stringResource(id = R.string.feed_create_post)) {
        onCreateFeedClicked()
    }
}

@Composable
fun CreatePostScreen() {
    YellowColumn {
        var postText by rememberSaveable { mutableStateOf("") }
        // Defaults to TextPost by using true. Should be replaced with Enum if more types need support.
        var postType: Boolean by rememberSaveable { mutableStateOf(true) }
        var image: Uri? by rememberSaveable { mutableStateOf(null) }
        // TODO move this gallary block to viewModel once built
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? -> uri?.let { image = it } }
        )
        val openGallery = { imagePickerLauncher.launch("image/*") }
        if (postType) {
            TextPost(
                currentText = postText,
                onTextChange = { postText = it }
            )
        } else {
            ImagePost(uri = image) {
                openGallery()
            }
        }
        CustomBottomNavBar(
            defaultToggle = postType,
            centerButtonText = stringResource(id = R.string.post_submit),
            onSubmit = { /*TODO Create Logic to Send Post to viewModel and out Network */ }
        ) { postType = it }
    }
}

@Composable
fun ColumnScope.TextPost(
    modifier: Modifier = Modifier,
    currentText: String,
    onTextChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    BasicTextField(
        modifier = modifier
            .weight(1f)
            .fillMaxWidth()
            .imePadding()
            .background(MaterialTheme.colorScheme.inversePrimary),
        value = currentText,
        onValueChange = { if (it.length < POST_FIELD_TEXT_LIMIT) onTextChange(it) },
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun ColumnScope.ImagePost(
    modifier: Modifier = Modifier,
    uri: Uri?,
    onClick: () -> Unit,
) {
    if (uri == null) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = IMAGE_NO_DESCRIPTION,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(MaterialTheme.dimens.smallIcon)
            )
        }
    } else {
        // TODO put in viewModel logic once built
        val bitmap: Bitmap? =
            MediaStore.Images.Media.getBitmap(LocalContext.current.contentResolver, uri)
        AsyncImage(
            modifier = modifier
                .weight(1f)
                .clickable { onClick() },
            model = bitmap,
            contentDescription = IMAGE_NO_DESCRIPTION
        )
    }
}

@Composable
fun CustomBottomNavBar(
    modifier: Modifier = Modifier,
    defaultToggle: Boolean,
    centerButtonText: String,
    onSubmit: () -> Unit,
    onToggle: (Boolean) -> Unit,
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(MaterialTheme.dimens.paddingSmall),
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PillToggle(
                modifier = Modifier.wrapContentSize(),
                selected = defaultToggle,
            ) { onToggle(it) }
            SmallBlackButton(
                modifier = Modifier.width(150.dp),
                onClick = onSubmit,
                text = centerButtonText
            )
        }
    }
}

@Composable
fun PillToggle(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(MaterialTheme.dimens.paddingLarge)
            .background(Color.Black, MaterialTheme.shapes.extraLarge)
            .clickable { onSelectedChange(!selected) },
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (selected) MaterialTheme.colorScheme.tertiary else Color.Black,
                    MaterialTheme.shapes.extraLarge
                )
                .border(width = 2.dp, Color.Black, MaterialTheme.shapes.extraLarge)
                .padding(MaterialTheme.dimens.paddingMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_text_fields_24),
                contentDescription = stringResource(id = R.string.post_toggle_type_text),
                tint = if (selected) Color.Black else Color.White,
                modifier = Modifier.size(MaterialTheme.dimens.superSmallIcon)
            )
        }
        Box(
            modifier = Modifier
                .background(
                    if (!selected) MaterialTheme.colorScheme.tertiary else Color.Black,
                    MaterialTheme.shapes.extraLarge
                )
                .border(width = 2.dp, Color.Black, MaterialTheme.shapes.extraLarge)
                .padding(MaterialTheme.dimens.paddingMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = stringResource(id = R.string.post_toggle_type_image),
                tint = if (!selected) Color.Black else Color.White,
                modifier = Modifier.size(MaterialTheme.dimens.superSmallIcon)
            )
        }
    }
}