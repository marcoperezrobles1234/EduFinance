package com.example.edufinance.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenContainer,
    secondary = Gold,
    onSecondary = Color.White,
    background = BackgroundLight,
    surface = SurfaceLight,
    onSurface = TextDark,
    onBackground = TextDark,
    error = AccentRed
)

private val DarkColors = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = TextLight,
    primaryContainer = GreenContainerDark,
    secondary = GoldDark,
    onSecondary = TextLight,
    background = BackgroundDark,
    surface = SurfaceDark,
    onSurface = TextLight,
    onBackground = TextLight,
    error = AccentRed
)

@Composable
fun EduFinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

