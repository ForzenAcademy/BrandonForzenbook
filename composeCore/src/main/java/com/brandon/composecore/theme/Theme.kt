package com.brandon.composecore.theme

import android.util.DisplayMetrics
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.brandon.composecore.constants.ComposableConstants.SCREEN_SIZE_THRESHOLD

@Composable
fun ForzenBookTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val forzenColorScheme = if (isDarkTheme) forzenDarkColorScheme else forzenLightColorScheme
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val typography = if (screenWidth <= SCREEN_SIZE_THRESHOLD) smallTypography else largeTypography

    MaterialTheme(
        colorScheme = forzenColorScheme,
        typography = typography,
        shapes = Shapes,
    ) {
        content()
    }
}

val MaterialTheme.dimens: Dimensions
    get() = if (DisplayMetrics().widthPixels <= SCREEN_SIZE_THRESHOLD) SmallDimens else LargeDimens


private val forzenLightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Color.Black,
    inversePrimary = Grey40,
    secondary = Grey99,
    onSecondary = Color.Black,
    secondaryContainer = Color.White,
    onSecondaryContainer = Color.Black,
    tertiaryContainer = Color.Black,
    onTertiaryContainer = Color.White,
    tertiary = Yellow100,
    onTertiary = Grey80,
    error = Red40,
    onSurfaceVariant = Color.Magenta,
)

private val forzenDarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.Black,
    primaryContainer = Color.Black,
    onPrimaryContainer = Color.White,
    inversePrimary = Grey40,
    secondary = Grey99,
    onSecondary = Color.Black,
    secondaryContainer = Color.White,
    onSecondaryContainer = Color.Black,
    tertiaryContainer = Color.Black,
    onTertiaryContainer = Color.White,
    tertiary = Yellow100,
    onTertiary = Grey80,
    error = Red80,
    onSurfaceVariant = Color.Magenta,
)