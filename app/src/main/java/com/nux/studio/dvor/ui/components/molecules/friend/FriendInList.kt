package com.nux.studio.dvor.ui.components.molecules.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.texts.Body1
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH6

@Composable
fun FriendInList(
    user: User,
    onClickFriend: () -> Unit,
    onAdd: () -> Unit,
    clickedFriend: Boolean = false,
    clickedAdd: Boolean = false
) {
    val modifier: Modifier = if (clickedFriend) Modifier
        .padding(bottom = 5.dp)
        .border(3.dp, MaterialTheme.colors.primary)
        .fillMaxWidth()
    else Modifier
        .padding(bottom = 5.dp)
        .fillMaxWidth()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .clickable(onClick = onClickFriend)
                .padding(10.dp)
        ) {
            AsyncImage(
                model = user.profile_pic,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                    .align(Alignment.CenterVertically),
                contentScale = Crop,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp, end = 50.dp)
            ) {
                Column {
                    HeadlineH6(
                        text = user.name,
                        modifier = Modifier,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Medium,
                    )

                    Body1(
                        text = "@${user.nickname}",
                        color = MaterialTheme.colors.onPrimary,
                    )
                }
                StripFriend(user = user)
            }
        }

        val imageId = if (clickedAdd) {
            R.drawable.check_circle
        } else {
            R.drawable.add_circle
        }

        Image(
            imageVector = ImageVector.vectorResource(id = imageId),
            contentDescription = "Add",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(onClick = {
                    onAdd()
                })
                .padding(10.dp)
        )
    }
}