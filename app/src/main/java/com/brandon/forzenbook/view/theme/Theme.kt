package com.brandon.forzenbook.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import com.brandon.forzenbook.view.composables.ComposableConstants.SCREEN_SIZE_THRESHOLD

@Composable
fun ForzenBookTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val forzenColorScheme = if (isDarkTheme) forzenDarkColorScheme else forzenLightColorScheme
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dimens = if (screenWidth <= SCREEN_SIZE_THRESHOLD) SmallDimens else LargeDimens
    val typography = if (screenWidth <= SCREEN_SIZE_THRESHOLD) smallTypography else largeTypography

    MaterialTheme(
        colorScheme = forzenColorScheme,
        typography = typography,
        shapes = Shapes,
    ) {
        ProvideTextStyle(
            value = TextStyle(color = forzenColorScheme.onPrimaryContainer),
            content = content
        )
        ProvideDimens(
            dimens = dimens,
            content = content
        )
    }
}

private val forzenLightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Color.Black,
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