package com.ch3x.chatlyzer.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GoldenYellow,
    onPrimary = TextWhite,
    secondary = WarmAmber,
    onSecondary = TextWhite,
    tertiary = TealAccent,
    background = BackgroundDark,
    onBackground = TextWhite,
    surface = SurfaceDark,
    onSurface = TextWhite,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = TextGray,
    error = ErrorRed
)

// For now, we want to enforce the dark/premium look even in light mode, 
// or provide a very specific light mode. Let's make Light mode a slightly lighter version 
// but still keeping the brand identity.
private val LightColorScheme = lightColorScheme(
    primary = GoldenYellow,
    onPrimary = Color.White,
    secondary = WarmAmber,
    onSecondary = Color.White,
    tertiary = TealAccent,
    background = Color(0xFFF5F5F7), // Light gray background
    onBackground = Color(0xFF121212),
    surface = Color.White,
    onSurface = Color(0xFF121212),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF757575),
    error = ErrorRed
)

@Composable
fun ChatlyzerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled to maintain the premium brand look
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}