package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ledokol.thebestprojectever.ui.components.atoms.*

@Preview
@Composable
fun TestScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextFieldFull()
        Body1(text = "Aliquam quos")
        Body2(text = "Aliquam quos")
        Caption(text = "Aliquam quos")
        HeadlineH1(text = "Aliquam quos")
        HeadlineH2(text = "Aliquam quos")
        HeadlineH3(text = "Aliquam quos")
        HeadlineH4(text = "Aliquam quos")
        HeadlineH5(text = "Aliquam quos")
        HeadlineH6(text = "Aliquam quos")
        Subtitle1(text = "Aliquam quos")
        Subtitle2(text = "Aliquam quos")
    }
}

@Composable
fun TextFieldFull(){
    val (text,setText) = remember { mutableStateOf("asdads") }
    TextField(label = "Label", text = text, onValueChange = { setText(it) })
}