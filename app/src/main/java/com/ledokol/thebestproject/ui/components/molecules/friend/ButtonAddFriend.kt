package com.ledokol.thebestproject.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6

@Composable
fun ButtonAddFriend(
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.secondary)
        ) {
            Icon(
                ImageBitmap.imageResource(id = R.drawable.plus),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp, 50.dp)
                    .padding(13.dp),
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp)
                .align(CenterVertically)
        ) {
            HeadlineH6(
                text = stringResource(id = R.string.add_friend_title),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                fontWeight = FontWeight.Bold,
            )
            Body1(
                text = stringResource(id = R.string.add_friend_description),
                modifier = Modifier,
//                    .padding(bottom = 10.dp)
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}