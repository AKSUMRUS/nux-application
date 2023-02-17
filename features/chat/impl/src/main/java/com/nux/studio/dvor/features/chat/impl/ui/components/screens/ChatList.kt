package com.nux.studio.dvor.features.chat.impl.ui.components.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nux.studio.dvor.core_ui.theme.TheBestProjectEverTheme
import com.nux.studio.dvor.features.chat.impl.filters.models.chatList.ChatType
import com.nux.studio.dvor.features.chat.impl.filters.models.chatList.ChatUI
import com.nux.studio.dvor.features.chat.impl.ui.components.molecules.ChatItem
import com.nux.studio.dvor.features.chat.impl.ui.components.molecules.ChatListTopBlock

@Preview
@Composable
private fun ChatsListPreview() {
    val hugeList = remember {
        List<ChatUI>(100) {
            ChatUI(
                time = "20:00",
                imageUrl = "https://sun9-19.userapi.com/impg/mhYE4EqTbXiScv9brpuyiYfx1oT8fwtzWi4p_Q/1rMmXLSlhNs.jpg?size=1920x1080&quality=95&sign=6df657cc9809b937b58a4decb68de0a3&type=album",
                senderName = "Хвойненький",
                chatContent = "Оформим партейку?",
                chatType = ChatType.IN_GAME
            )
        }
    }
    TheBestProjectEverTheme() {
        ChatsList(
            searchText = "Some Text",
            onSearchTextChange = {},
            chats = hugeList
        )
    }
}

@Composable
fun ChatsList(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    chats: List<ChatUI>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ChatListTopBlock(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange
            )
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it)
        ) {
            items(chats) { chat ->
                ChatItem(
                    time = chat.time,
                    imageUrl = chat.imageUrl,
                    senderName = chat.senderName,
                    chatContent = chat.chatContent,
                    chatType = chat.chatType,
                    onItemClick = { /*TODO*/ })
            }
        }
    }
}