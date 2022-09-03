package com.ledokol.thebestproject.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


//primary: Color,
//primaryVariant: Color,
//secondary: Color,
//secondaryVariant: Color,
//background: Color,
//surface: Color,
//error: Color,
//onPrimary: Color,
//onSecondary: Color,
//onBackground: Color,
//onSurface: Color,
//onError: Color,
//isLight: Boolean

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