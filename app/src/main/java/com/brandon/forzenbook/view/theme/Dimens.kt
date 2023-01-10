package com.brandon.forzenbook.view.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimens = compositionLocalOf { Dimens() }

// TODO FA-81 Add getter for different sizes based on screen size
data class Dimens(
    val paddingExtraSmall: Dp = 2.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingExtraLarge: Dp = 16.dp,
    val buttonHeight: Dp = 40.dp,
    val circularLoadingIndicator: Dp = 200.dp
    )