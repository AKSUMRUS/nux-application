package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.material.Button

@Composable
// Обычная кнопка с текстом внутри (по центру)
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
) {

    Button(
        modifier = modifier,
        shape = shape,
        onClick = onClick){
        Body2(text = text)
    }

}

