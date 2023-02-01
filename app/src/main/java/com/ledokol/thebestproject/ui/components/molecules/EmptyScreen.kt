package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5

@Composable
fun EmptyScreen(
    title: String,
) {

    Box(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        HeadlineH5(
            text = title,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
        )
    }

}