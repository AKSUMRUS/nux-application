package com.ledokol.dvor.ui.components.atoms.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.dvor.ui.components.atoms.texts.Body1

@Composable
fun ButtonPrimaryLeadingIcon(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .border(2.dp, MaterialTheme.colors.secondary),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
        )
    ) {
        Row {
            Icon(
                icon,
                null,
                modifier = Modifier
                    .size(30.dp)
                    .align(CenterVertically),
                tint = MaterialTheme.colors.secondary,
            )

            Body1(
                text = text,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(CenterVertically),
                fontWeight = FontWeight.W700,
            )

        }
    }
}