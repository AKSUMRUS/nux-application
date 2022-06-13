package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ledokol.thebestprojectever.ui.components.atoms.*

@Preview(showBackground = true)
@Composable
fun QuickGameScreen(){
    Column(
    ) {
        Body1(text = "Quick game", modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
            type = "surface"
        )

    }
}