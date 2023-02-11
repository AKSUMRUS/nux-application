package com.ledokol.thebestproject.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.features.chat.impl.ui.components.InternalChatFeatureRoutes
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6
import com.nux.studio.dvor.core_ui.atoms.CircleAsyncImageWithBorder

/**
 * Элемент списка чатов
 */
@Composable
fun ChatsItem(navController: NavController) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .size(1.dp)
            .background(MaterialTheme.colors.onPrimary)
    )
    Box(
        modifier = Modifier
        .fillMaxWidth()
        .clickable { navController.navigate(InternalChatFeatureRoutes.chat()) }
    ) {
        Row {
            CircleAsyncImageWithBorder(
                url = "https://sun9-6.userapi.com/impg/zmzFaRBkJtt_KwMGd41ARQyNMRxIctDLPD3uCg/U3HSrag1wIw.jpg?size=1035x1280&quality=95&sign=846b0408cc33466822f75ec8a3728431&type=album",
                description = "friend avatar image",
                borderColor = MaterialTheme.colors.secondary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                HeadlineH5(
                    text = "Name"
                )
                HeadlineH6(
                    text = "Last message",
                )
            }
        }
        HeadlineH6(
            text = "12:00",
            modifier = Modifier
                .align(Alignment.TopEnd)
        )
    }
}