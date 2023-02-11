package com.ledokol.thebestproject.features.chat.impl.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.features.chat.impl.ui.components.molecules.ChatTopBlock
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4

@Composable
fun Chat() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 20.dp, bottom = 20.dp)
        ) {
        ChatTopBlock()
    }

}