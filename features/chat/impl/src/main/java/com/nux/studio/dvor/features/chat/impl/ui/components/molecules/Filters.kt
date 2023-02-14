package com.nux.studio.dvor.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.core_ui.atoms.buttons.ButtonFull
import com.nux.studio.dvor.features.chat.impl.R

@Composable
fun Filters() {
    Row {
        ButtonFull(
            text = stringResource(id = R.string.status_in_search),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            onClick = { }
        )
        ButtonFull(
            text = stringResource(id = R.string.status_in_game),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            onClick = { }
        )
        ButtonFull(
            text = stringResource(id = R.string.status_not_active),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            onClick = { }
        )
    }

}