package com.ledokol.thebestprojectever.ui.components.molecules.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@Composable
fun DisturbButton(
    modifier: Modifier = Modifier
        .background(MaterialTheme.colors.primary),
    color: Color = MaterialTheme.colors.onPrimary,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
        ) {
            Image(
                painterResource(id = R.drawable.moon),
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 20.dp, width = 20.dp)
                    .align(CenterVertically),
                contentScale = ContentScale.Crop,
            )

            Body1(
                text = stringResource(id = R.string.dontdisturb),
                color = color,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}
