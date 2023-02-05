package com.ledokol.dvor.ui.components.molecules.friend

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ledokol.dvor.data.local.user.User
import com.ledokol.dvor.ui.components.atoms.Cross
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonFull
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH2
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH6

@Composable
fun PreviewFriend(
    navController: NavController,
    user: User,
    showButtonAddFriend: Boolean,
    sendInvite: Boolean,
    inviteFriends: () -> Unit,
    onClickCross: () -> Unit,
) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
//            .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                user.profile_pic,
                contentDescription = null,
                modifier = Modifier
                    .size(height = 150.dp, width = 150.dp)
                    .padding(bottom = 10.dp),
                alignment = Alignment.Center,
                contentScale = Crop,
            )

            HeadlineH2(
                text = user.name,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                fontWeight = Bold,
            )

            HeadlineH6(
                text = "@${user.nickname}",
                modifier = Modifier
                    .padding(bottom = 20.dp),
                color = MaterialTheme.colors.onPrimary,

                )


            if (showButtonAddFriend) {
                ButtonFull(
                    text = if (!sendInvite) "Добавить в друзья" else "Готово",
                    onClick = {
                        if (!sendInvite) {
                            inviteFriends()
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    padding = PaddingValues(
                        start = 35.dp,
                        end = 35.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    ),
                    colorBackground = if (sendInvite) MaterialTheme.colors.surface else MaterialTheme.colors.secondary,
                    colorText = if (sendInvite) MaterialTheme.colors.onSurface else MaterialTheme.colors.onBackground,
                )
            }
        }

        Cross(
            onClick = {
                onClickCross()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}