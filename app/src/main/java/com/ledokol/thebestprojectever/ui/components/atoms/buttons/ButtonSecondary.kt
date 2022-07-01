package com.ledokol.thebestprojectever.ui.components.atoms.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.Body1

@Composable
fun ButtonSecondary(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant,
        )
    ) {
        Body1(
            text = text,
            modifier = Modifier.padding(10.dp)
        )
    }
}