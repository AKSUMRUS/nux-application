package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4

@Composable
fun ScreenTitle(
    name: String,
    description: String? = null
) {

    Column(
        modifier = Modifier.padding(bottom = 20.dp),
    ) {
        HeadlineH4(
            text = name,
            fontWeight = FontWeight.W700,
        )
        if(description != null) {
            Body1(
                text = description,
                color = MaterialTheme.colors.onBackground,
            )
        }
    }

}