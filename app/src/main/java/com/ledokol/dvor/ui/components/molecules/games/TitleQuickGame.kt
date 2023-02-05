package com.ledokol.dvor.ui.components.molecules.games

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.ledokol.dvor.ui.components.atoms.texts.Body1
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH4

@Composable
fun TitleQuickGame(
    title: String,
    modifier: Modifier = Modifier,
    description: String = "",
) {

    Column(
        modifier = modifier,
    ) {
        HeadlineH4(
            text = title,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
        )
        Body1(
            text = description,
            color = MaterialTheme.colors.onPrimary,
        )
    }
}