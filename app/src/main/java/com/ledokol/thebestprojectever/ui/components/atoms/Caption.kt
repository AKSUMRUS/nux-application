package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Caption(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        modifier = modifier
    )
}

