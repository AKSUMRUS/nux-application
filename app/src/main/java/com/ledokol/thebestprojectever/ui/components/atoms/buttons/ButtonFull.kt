package com.ledokol.thebestprojectever.ui.components.atoms.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonFull(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colorBackground: Color = MaterialTheme.colors.primary,
    colorText: Color = MaterialTheme.colors.onPrimary,
){

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .background(colorBackground)
    ,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorBackground,
        )
    ) {
        HeadlineH6(
            text = text,
            color = colorText,
            modifier = Modifier.padding(5.dp)
        )
    }
}