package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH6

@Composable
fun TitleQuickGame(
    step: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
){

    Column(
        modifier = modifier,
    ){
        Body1(
            text = stringResource(id = R.string.step)+" "+step.toString(),
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.W500,
        )
        HeadlineH4(
            text = title,
            fontWeight = FontWeight.W700,
        )
        HeadlineH6(
            text = description,
            color = MaterialTheme.colors.onBackground,
        )
    }
}