package com.nux.studio.dvor.ui.components.molecules.friend

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nux.dvor.ui.components.atoms.buttons.ButtonEmpty
import com.nux.dvor.ui.components.atoms.buttons.ButtonLittleBorder
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH6

@Composable
fun FriendTopBar(
    user: User,
    onClickClaim: () -> Unit = {},
    onRemoveFriend: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 80.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
//                    .align(Alignment.CenterVertically)
                .weight(2f),
        ) {
            HeadlineH4(
                text = user.name,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W700,
            )

            HeadlineH6(
                text = "@${user.nickname}",
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.W700,
            )

            StripFriend(
                user = user,
            )

            ButtonLittleBorder(
                text = stringResource(id = R.string.remove_friend),
                onClick = {
                    onRemoveFriend()
                },
                padding = 0.dp,
                colorBorder = MaterialTheme.colors.error,
            )


            ButtonEmpty(text = "Пожаловаться", onClick = { onClickClaim() })
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AsyncImage(
                model = user.profile_pic,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 120.dp, width = 120.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}