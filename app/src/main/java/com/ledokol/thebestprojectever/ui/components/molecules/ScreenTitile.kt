package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4

@Composable
fun ScreenTitile(
    name: String,
    description: String? = null
) {

    Column() {
        HeadlineH4(
            text = name,
            fontWeight = FontWeight.W700,
        )
        if(description != null) {
            Body1(
                text = description,
                type = "background"
            )
        }
    }

}