package com.nux.studio.dvor.features.chat.impl.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nux.studio.dvor.features.chat.impl.ui.components.molecules.ChatsItem
import com.nux.studio.dvor.features.chat.impl.ui.components.molecules.ChatsListTopBLock

@Composable
fun ChatsList(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        ChatsListTopBLock()

        repeat(6) {
            ChatsItem(navController = navController)
        }
    }

}