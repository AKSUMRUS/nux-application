package com.nux.studio.dvor.ui.components.atoms.texts

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HeadlineH4(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    fontWeight: FontWeight? = MaterialTheme.typography.h4.fontWeight,
    textAlign: TextAlign = TextAlign.Left,
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.h4,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign,
    )
}

