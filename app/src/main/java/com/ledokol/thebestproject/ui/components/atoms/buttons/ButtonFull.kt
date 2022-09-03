package com.ledokol.thebestproject.ui.components.atoms.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonFull(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colorBackground: Color = MaterialTheme.colors.primary,
    colorText: Color = MaterialTheme.colors.onPrimary,
    padding: Dp = 10.dp,
){

    Button(
        onClick = {onClick()},
        modifier = Modifier
            .then(modifier)
    ,
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorBackground,
        ),
        contentPadding = PaddingValues(padding)

//        elevation = ButtonElevation.elevation(null)
    ) {
        HeadlineH6(
            text = text,
            color = colorText,
            modifier = Modifier
//                .fillMaxWidth()
                .align(CenterVertically)
                .padding(bottom = 2.dp)
        ,
            textAlign = TextAlign.Center
        )
    }
}