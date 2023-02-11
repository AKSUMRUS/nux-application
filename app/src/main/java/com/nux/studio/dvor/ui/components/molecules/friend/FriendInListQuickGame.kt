package com.nux.studio.dvor.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.ui.components.atoms.Status
import com.nux.studio.dvor.ui.components.atoms.texts.Body1
import com.nux.studio.dvor.ui.components.atoms.texts.Body2

@Composable
fun FriendInListQuickGame(
    user: User,
    onClick: () -> Unit,
    clicked: Boolean = false,
) {

    val modifier: Modifier = if (clicked) Modifier
        .padding(bottom = 20.dp)
//        .border(3.dp, MaterialTheme.colors.primary)
        .fillMaxWidth()
    else Modifier
        .padding(bottom = 20.dp)
        .fillMaxWidth()

    Box(
        modifier = modifier
            .background(MaterialTheme.colors.primary),
    ) {

        if (user.status.in_app && user.status.app != null) {
            AsyncImage(
                user.status.app!!.image_wide,
                contentDescription = "user back",
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                alpha = 0.3f,
                contentScale = Crop
            )
        }

        Row(
            modifier = Modifier
                .height(84.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .clickable(onClick = onClick)
                .padding(12.dp)
        ) {
            AsyncImage(
                model = user.profile_pic,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(CenterVertically),
                contentScale = Crop,
            )

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(CenterVertically)
            ) {
                Body1(
                    text = user.nickname,
                    modifier = Modifier,
                    color = MaterialTheme.colors.onBackground
                )

                Row(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Status(
                        status = if (user.status.online) "online" else "offline",
                        modifier = Modifier
                            .padding(top = 3.dp, end = 5.dp)
                            .align(CenterVertically)
//                            .fillMaxHeight()
                            .align(CenterVertically),
                        size = 10.dp,
                    )
                    Body2(
                        text = if (user.status.online) "online" else "offline",
                        modifier = Modifier
                            .padding()
                            .alpha(0.5f),
                    )
                }
            }
        }

        Checkbox(
            checked = clicked,
            onCheckedChange = { onClick() },
            modifier = Modifier
                .padding(end = 10.dp)
//                .size(height = 100.dp, width = 100.dp)
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(0.dp)),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.secondary,
                uncheckedColor = MaterialTheme.colors.secondaryVariant,
                checkmarkColor = MaterialTheme.colors.onBackground,

                )
        )
    }
}