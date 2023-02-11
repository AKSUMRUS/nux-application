package com.nux.studio.dvor.ui.components.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Status(
    status: String,
    modifier: Modifier = Modifier,
    size: Dp = 15.dp
) {
    Canvas(modifier = modifier.size(size),
        onDraw = {
            if (status == "offline") {
                drawCircle(color = Color.Gray)
            } else {
                drawCircle(color = Color.Green)
            }
        })

}