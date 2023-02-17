package com.nux.studio.dvor.features.chat.impl.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nux.studio.dvor.core_ui.atoms.CircleAsyncImageWithBorder
import com.nux.studio.dvor.core_ui.theme.SansPro
import com.nux.studio.dvor.core_ui.theme.TheBestProjectEverTheme
import com.nux.studio.dvor.features.chat.impl.filters.models.chatList.ChatType
import com.nux.studio.dvor.features.chat.impl.filters.models.chatList.getColor

@Preview
@Composable
private fun ChatItemPreview() {
    TheBestProjectEverTheme(darkTheme = true) {
        ChatItem(
            modifier = Modifier.background(Color(0xFF13121B)),
            onItemClick = { /*TODO*/ },
            imageUrl = "https://sun9-19.userapi.com/impg/mhYE4EqTbXiScv9brpuyiYfx1oT8fwtzWi4p_Q/1rMmXLSlhNs.jpg?size=1920x1080&quality=95&sign=6df657cc9809b937b58a4decb68de0a3&type=album",
            senderName = "Гордей",
            chatType = ChatType.IN_GAME,
            chatContent = "Нет, ну, а как вы хотели, я тоже не на что че длинный текст длинный текст длинный текст",
            time = "13:42"
        )
    }
}

/**
 * Элемент списка чатов
 */
@Composable
fun ChatItem(
    time: String,
    imageUrl: String,
    senderName: String,
    chatContent: String,
    chatType: ChatType,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onItemClick() }
    ) {
        ChatSpacer()
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)) {
            CircleAsyncImageWithBorder(
                modifier = Modifier.align(Alignment.CenterVertically),
                url = imageUrl,
                description = "friend avatar image",
                borderColor = chatType.getColor(),
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = senderName,
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.W700,
                            fontSize = 16.sp,
                            letterSpacing = (0.15).sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = time,
                        color = Color(0xFF6B659E),
                        style = MaterialTheme.typography.caption.copy(fontFamily = SansPro),
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = chatContent,
                    color = Color(0xFF6B659E),
                    style = MaterialTheme.typography.caption.copy(fontFamily = SansPro),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        ChatSpacer()
    }
}

@Composable
private fun ChatSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .size(1.dp)
            .background(MaterialTheme.colors.onPrimary),
    )
}