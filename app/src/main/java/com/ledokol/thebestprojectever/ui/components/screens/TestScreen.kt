package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ledokol.thebestprojectever.ui.components.atoms.Button

@Composable
fun TestScreen(

) {
    Column() {
        Button(text = "Test Crash", onClick = { throw RuntimeException("Test Crash") })
    }
}