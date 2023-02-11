package com.nux.studio.dvor.ui.components.atoms.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonRightIcon(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageBitmap,
    colorBackground: Color = MaterialTheme.colors.primary,
    colorText: Color = MaterialTheme.colors.onPrimary,
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .background(colorBackground),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorBackground,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            HeadlineH6(
                text = text,
                color = colorText,
                modifier = Modifier
                    .padding(5.dp)
                    .align(CenterVertically)
                    .weight(5f),
                fontWeight = W400,
            )


            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier
                    .size(height = 20.dp, width = 20.dp)
                    .align(CenterVertically)
                    .weight(1f),
                tint = Color.White
            )
        }

    }
}