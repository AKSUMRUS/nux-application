package com.ledokol.thebestproject.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.core_ui.atoms.Search

@Composable
fun ChatsListTopBLock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Search(text = "", onValueChange = {})
        Filters()
    }
}