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
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1

@Composable
fun ButtonEmpty(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colorBackground: Color = MaterialTheme.colors.secondary,
    colorText: Color = MaterialTheme.colors.error,
    padding: PaddingValues = PaddingValues(0.dp, 0.dp, 10.dp, 10.dp),
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier),
        shape = RoundedCornerShape(34.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        contentPadding = padding,

//        elevation = ButtonElevation.elevation(null)
    ) {
        Body1(
            text = text,
            color = colorText,
            modifier = Modifier
//                .fillMaxWidth()
                .align(CenterVertically)
                .padding(bottom = 2.dp),
            textAlign = TextAlign.Center
        )
    }
}