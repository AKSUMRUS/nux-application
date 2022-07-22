package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.material.TextButton
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Subtitle1

@Composable
// Кликабельный текст
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small
    ) {

    TextButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape
    ) {
        Subtitle1(text = text)
    }

}

