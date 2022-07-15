package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Status(
    status: String,
    modifier: Modifier = Modifier,
){
    Canvas(modifier = modifier.size(15.dp)
        ,
        onDraw = {
            if(status == "offline") {
                drawCircle(color = Color.Gray)
            }
            else {
                drawCircle(color = Color.Green)
            }
        })

}