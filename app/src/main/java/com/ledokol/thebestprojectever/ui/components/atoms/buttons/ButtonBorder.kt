package com.ledokol.thebestprojectever.ui.components.atoms.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonBorder(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    padding: Dp = 10.dp,
    colorBackground: Color = MaterialTheme.colors.background,
    colorBorder:Color = MaterialTheme.colors.primary,
    colorText: Color = colorBorder,
){

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .border(2.dp, colorBorder)
            .clip(RoundedCornerShape(0.dp))
    ,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorBackground,
        )
    ) {
        HeadlineH6(
            text = text,
            color = colorText,
            modifier = Modifier.padding(padding)
        )
    }
}