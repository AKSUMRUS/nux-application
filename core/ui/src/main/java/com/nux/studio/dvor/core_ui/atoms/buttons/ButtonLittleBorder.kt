package com.nux.dvor.ui.components.atoms.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.core_ui.atoms.texts.Body2

@Composable
fun ButtonLittleBorder(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    padding: Dp = 2.dp,
    colorBackground: Color = MaterialTheme.colors.background,
    colorBorder: Color = MaterialTheme.colors.onBackground,
    colorText: Color = colorBorder,
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
//            .border(1.dp, colorBorder, RoundedCornerShape(16.dp))
            .padding(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorBackground,
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Body2(
            text = text,
            color = colorText,
            modifier = Modifier.padding(padding)
        )
    }
}