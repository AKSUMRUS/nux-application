package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Body1(
    text: String,
    modifier: Modifier = Modifier,
    type: String = "surface",
) {

    val color: Color

    when(type) {
        "surface" -> color = MaterialTheme.colors.onSurface
        "primary" -> color = MaterialTheme.colors.onPrimary
        "secondary" -> color = MaterialTheme.colors.onSecondary
        "background" -> color = MaterialTheme.colors.onBackground
        else -> color = MaterialTheme.colors.onError
    }

    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.body1,
        modifier = modifier
    )
}

