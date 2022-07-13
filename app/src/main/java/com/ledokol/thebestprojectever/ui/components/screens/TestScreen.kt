package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary

@Composable
fun TestScreen(

) {
    Column(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        ButtonPrimary(text = "Test Crash", onClick = { throw RuntimeException("Test Crash") })
    }
}