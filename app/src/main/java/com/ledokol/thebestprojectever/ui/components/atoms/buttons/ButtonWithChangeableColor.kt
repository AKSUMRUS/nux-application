package com.ledokol.thebestprojectever.ui.components.atoms.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonWithChangeableColor(
    isClicked: Boolean,
    onClick: () -> Unit,
    color1: Color,
    color1Text: Color,
    color2: Color,
    color2Text: Color,
    text1: String,
    text2: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
        ,
        colors = ButtonDefaults.buttonColors(
                backgroundColor = if(!isClicked) color1 else color2,
        ),
        shape = RoundedCornerShape(0.dp),
    ) {
        HeadlineH6(
            text = if(!isClicked) text1 else text2,
            color = if(!isClicked) color1Text else color2Text,
        )
    }
}