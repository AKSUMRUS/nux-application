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
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH2
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH3
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6

@Composable
fun TitleRegistration(
    title: String,
    description: String,
){
    Column(
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        HeadlineH3(
            text = title,
            fontWeight = FontWeight.W700,
        )
        HeadlineH6(
            text = description,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colors.onBackground,
        )
    }

}