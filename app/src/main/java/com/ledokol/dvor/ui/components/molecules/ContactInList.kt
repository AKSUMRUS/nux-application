package com.ledokol.dvor.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.dvor.R
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonFull
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH6

@Composable
fun ContactInList(
    name: String,
    textButton: String = stringResource(id = R.string.invite_contact),
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(15.dp)
    ) {
        HeadlineH6(
            text = name,
            modifier = Modifier
                .weight(4f)
                .padding(end = 10.dp)
//                .align(Alignment.CenterStart)
            ,
            color = MaterialTheme.colors.onBackground
        )
        ButtonFull(
            text = textButton,
            onClick = {
                onClick()
            },
            padding = PaddingValues(3.dp),
            modifier = Modifier
//                .align(Alignment.CenterStart)
                .weight(3f)
//                .align(Arrangement.End)
//                .height(40.dp)
        )
    }
}