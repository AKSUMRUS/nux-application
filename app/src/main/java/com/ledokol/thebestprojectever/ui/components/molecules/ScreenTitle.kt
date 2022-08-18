package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4

@Composable
fun ScreenTitle(
    name: String,
    description: String? = null,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = Modifier.padding(bottom = 20.dp).then(modifier),
    ) {
        HeadlineH4(
            text = name,
            fontWeight = FontWeight.W500,
        )
        if(description != null) {
            Body1(
                text = description,
                color = MaterialTheme.colors.secondaryVariant,
            )
        }
    }

}