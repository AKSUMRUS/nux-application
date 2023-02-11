package com.nux.studio.dvor.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.ui.components.atoms.buttons.ButtonBorder

@Composable
fun TestScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        ButtonBorder(text = "Test Crash", onClick = { throw RuntimeException("Test Crash") })
    }
}