package com.nux.studio.dvor.features.chat.impl.filters.models.chatList

data class ChatUI(
    val time: String,
    val imageUrl: String,
    val senderName: String,
    val chatContent: String,
    val chatType: ChatType,
)