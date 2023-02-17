package com.nux.studio.dvor.features.chat.impl.filters.models.chatList

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ChatType {
    IN_SEARCH,
    NOT_ACTIVE,
    IN_GAME
}

@Composable
fun ChatType.getColor(): Color =
    when (this) {
       ChatType.IN_SEARCH -> Color(0xFF835FF)
       ChatType.IN_GAME -> Color(0xFF71C82D)
        ChatType.NOT_ACTIVE -> Color(0xFF6F6F6F)
    }