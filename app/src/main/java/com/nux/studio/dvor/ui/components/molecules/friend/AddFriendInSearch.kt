package com.nux.studio.dvor.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nux.studio.dvor.core_ui.atoms.texts.Body1
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH6
import com.nux.studio.dvor.data.local.user.User

@Composable
fun AddFriendInSearch(
    user: User,
    addFriend: () -> Unit,
    openFriend: () -> Unit,
    clicked: Boolean = false,
) {

    val modifier: Modifier = if (clicked) Modifier
        .padding(bottom = 5.dp)
        .border(3.dp, MaterialTheme.colors.primary)
        .fillMaxWidth()
    else Modifier
        .padding(bottom = 5.dp)
        .fillMaxWidth()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary)
            .clickable { openFriend() },
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .padding(10.dp)
        ) {
            AsyncImage(
                model = user.profile_pic,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                    .align(CenterVertically),
                contentScale = Crop,
                alignment = Alignment.Center,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(0.dp)
                ) {
                    HeadlineH6(
                        text = user.name,
                        modifier = Modifier,
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Medium,
                    )

                    Body1(
                        text = "@${user.nickname}",
                        modifier = Modifier
                            .padding(start = 10.dp),
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                }

                StripFriend(user = user, addFriend = true, onClick = {
                    addFriend()
                })

            }

        }
    }
}