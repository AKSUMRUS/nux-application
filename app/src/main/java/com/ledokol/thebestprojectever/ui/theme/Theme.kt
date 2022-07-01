package com.ledokol.thebestprojectever.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple,
    onPrimary = White,
    primaryVariant = Gray2,
    secondary = Gray4,
    onSecondary = Gray3,
    background = Black2,
    onBackground = Gray2,
    surface = Gray,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = Purple,
    onPrimary = White,
    primaryVariant = Gray2,
    secondary = Gray4,
    onSecondary = Gray3,
    background = Black2,
    onBackground = Gray2,
    surface = Gray,
    onSurface = White,


    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun TheBestProjectEverTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
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