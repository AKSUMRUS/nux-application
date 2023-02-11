package com.nux.studio.dvor.ui.components.molecules.friend

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH4

@Composable
fun TitleFriends(
    text: String,
    modifier: Modifier = Modifier,
) {

    HeadlineH4(
        text = text,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .padding(top = 20.dp, bottom = 0.dp)
            .then(modifier)
    )


}