    package com.nux.studio.dvor.core_ui.atoms

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * Draw circle image with border by url
 */
@Composable
fun CircleAsyncImageWithBorder(
    url: String,
    description: String,
    borderColor: Color,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = url,
        contentDescription = description,
        modifier = modifier.then(
            Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = borderColor,
                    shape = CircleShape
                )
        ),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun CircleAsyncImagePreview() {
    CircleAsyncImageWithBorder(
        url = "https://sun9-6.userapi.com/impg/zmzFaRBkJtt_KwMGd41ARQyNMRxIctDLPD3uCg/U3HSrag1wIw.jpg?size=1035x1280&quality=95&sign=846b0408cc33466822f75ec8a3728431&type=album",
        description = "friend avatar image",
        borderColor = MaterialTheme.colors.primary
    )
}