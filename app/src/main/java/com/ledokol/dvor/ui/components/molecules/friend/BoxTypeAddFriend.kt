package com.ledokol.dvor.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH6

@Composable
fun BoxTypeAddFriend(
    icon: ImageBitmap,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 7.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colors.primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(height = 60.dp, width = 60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.secondary)
                .padding(14.dp),
            contentDescription = null,
            tint = Color.White,
        )

        HeadlineH6(
            text = title,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(start = 26.dp)
        )

    }

}