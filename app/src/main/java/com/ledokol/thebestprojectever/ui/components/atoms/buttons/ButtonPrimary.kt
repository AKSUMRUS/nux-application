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
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6

@Composable
fun ButtonPrimary(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit,
){

    Button(
        onClick = onClick,
        modifier = Modifier
            .then(modifier)
            .border(2.dp, MaterialTheme.colors.primary)
    ,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
        )
    ) {
        HeadlineH6(
            text = text,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(10.dp)
        )
    }
}