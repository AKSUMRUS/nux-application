package com.nux.dvor.ui.components.atoms.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithImage(
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
//            .then(modifier)
//            .height(30.dp)
        shape = RoundedCornerShape(0.dp),
    ) {
        Image(painter = icon, contentDescription = "")
    }
}