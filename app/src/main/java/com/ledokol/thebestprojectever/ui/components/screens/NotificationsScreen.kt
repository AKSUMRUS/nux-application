package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.notifications.NotificationEntity
import com.ledokol.thebestprojectever.data.local.notifications.NotificationsEvent
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.NotificationsViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitleFriends
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInList

@Composable
fun NotificationsScreen(
    notificationsViewModel: NotificationsViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel : UserViewModel,
    navController : NavController
) {

    val state = notificationsViewModel.state
    val usersList = state.friendInvites
    val token = profileViewModel.state.profile?.access_token.toString()

    LaunchedEffect(true) {
        notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
    }

    fun onClick(
        navController: NavController,
        notificationEntity: NotificationEntity
    ){
        notificationsViewModel.onEvent(NotificationsEvent.AddFriend(token, notificationEntity))
//        navController.navigate("friend_screen") {
//            popUpTo("friend_screen")
//            launchSingleTop = true
//        }
    }

    LazyColumn(
        content = {
            item {
                ScreenTitle(
                    name = stringResource(id = R.string.nav_notifications),
                    modifier = Modifier.padding(start = 20.dp, top = 110.dp),
                )
            }

            items(usersList!!.size) { friend ->
                val user = usersList[friend].from_user
                FriendInList(
                    user = user,
                    onClick = {
                        onClick(
                            navController = navController,
                            notificationEntity = usersList[friend]
                        )
                    })
            }
        }
    )

}