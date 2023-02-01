package com.ledokol.thebestproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryVariant = GrayLow,
    secondaryVariant = Gray3,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = Gray5,
    onSurface = White,
    error = Red,
    onError = White,
)

private val LightColorPalette = lightColors(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryVariant = GrayLow,
    secondaryVariant = Gray3,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = Gray5,
    onSurface = White,
    error = Red,
    onError = White,
)

@Composable
fun TheBestProjectEverTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors =
        if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}