package com.ledokol.thebestproject.ui.components.atoms.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.ledokol.thebestproject.ui.components.atoms.texts.Body2

@Composable
fun GradientButton(
    text: String,
    gradient : Brush,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Body2(text = text)
        }
    }
}