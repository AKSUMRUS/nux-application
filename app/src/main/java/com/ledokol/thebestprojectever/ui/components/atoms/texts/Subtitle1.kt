package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Subtitle1(
    text: String,
    modifier: Modifier = Modifier,
    type: String = "surface",
    fontWeight: FontWeight? = MaterialTheme.typography.subtitle1.fontWeight,
) {

    val color: Color = getColorText(type)

    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.subtitle1,
        modifier = modifier,
        fontWeight = fontWeight,
        )
}

