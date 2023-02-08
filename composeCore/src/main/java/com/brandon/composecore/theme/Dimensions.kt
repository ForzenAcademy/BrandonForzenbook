package com.brandon.composecore.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProvideDimens(
    dimens: Dimensions,
    content: @Composable () -> Unit,
) {
    val dimenSets = remember { dimens }
    CompositionLocalProvider(LocalDimens provides dimenSets, content = content)
}

val LocalDimens = compositionLocalOf { LargeDimens }

data class Dimensions(
    val paddingExtraSmall: Dp,
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
    val paddingExtraLarge: Dp,
    val circularLoadingIndicator: Dp,
    val notificationDialogHeight: Dp,
    val notificationDialogWidth: Dp,
    val notificationDialogPadding: Dp,
    val notificationDialogImageSize: Dp,
    val largeIcon: Dp,
    val minimumTouchTargetPadding: Dp = 8.dp,
    val minimumTouchTarget: Dp = 48.dp,
)

val SmallDimens = Dimensions(
    paddingExtraSmall = 2.dp,
    paddingSmall = 4.dp,
    paddingMedium = 8.dp,
    paddingLarge = 12.dp,
    paddingExtraLarge = 16.dp,
    circularLoadingIndicator = 100.dp,
    notificationDialogHeight = 160.dp,
    notificationDialogWidth = 240.dp,
    notificationDialogImageSize = 150.dp,
    notificationDialogPadding = 12.dp,
    largeIcon = 200.dp,
)

val LargeDimens = Dimensions(
    paddingExtraSmall = 4.dp,
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingLarge = 16.dp,
    paddingExtraLarge = 20.dp,
    circularLoadingIndicator = 200.dp,
    notificationDialogHeight = 200.dp,
    notificationDialogWidth = 280.dp,
    notificationDialogImageSize = 200.dp,
    notificationDialogPadding = 12.dp,
    largeIcon = 250.dp
)