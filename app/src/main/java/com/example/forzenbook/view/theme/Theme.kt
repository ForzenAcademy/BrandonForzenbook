package com.example.forzenbook.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun ForzenBookTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val forzenColorScheme = when {
        isDarkTheme -> {
            forzenDarkColorScheme
        }
        else -> {
            forzenLightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = forzenColorScheme,
        typography = Typography,
        shapes = Shapes,
    ) {
        ProvideTextStyle(
            value = TextStyle(color = forzenColorScheme.onPrimaryContainer),
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
    tertiary = Yellow100,
    onTertiary = Color.Black,
    error = Red40,
)

private val forzenDarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.Black,
    primaryContainer = Color.Black,
    onPrimaryContainer = Color.White,
    secondary = Grey99,
    onSecondary = Color.Black,
    tertiary = Yellow100,
    onTertiary = Color.White,
    error = Red80,
)