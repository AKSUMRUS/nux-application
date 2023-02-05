package com.ledokol.dvor.ui.components.screens.friends

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.ledokol.dvor.data.local.user.UserEvent
import com.ledokol.dvor.presentation.ProfileViewModel
import com.ledokol.dvor.presentation.UserViewModel
import com.ledokol.dvor.ui.components.molecules.LoadingViewCenter
import com.ledokol.dvor.ui.components.molecules.friend.PreviewEmptyFriend
import com.ledokol.dvor.ui.components.molecules.friend.PreviewFriend

@Composable
fun PreviewFriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
) {
    val user = userViewModel.state.friendUser
    val state = userViewModel.state
    var sendInvite by remember { mutableStateOf(false) }
    var showButtonAddFriend by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true, block = {
        userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
    })

    LaunchedEffect(userViewModel.state.users, user) {
        val users = userViewModel.state.users
        if (user != null && user.id == profileViewModel.state.profile!!.id) {
            showButtonAddFriend = false
        }
        if (users != null && user != null && user.id != profileViewModel.state.profile!!.id) {
            Log.e("UsersFriendScreen", users.toString())
            for (userInList in users) {
                if (user.id == userInList.id) {
                    showButtonAddFriend = false
                }
            }
        }
    }

    fun inviteFriend() {
        sendInvite = true
        userViewModel.onEvent(
            UserEvent.AddFriend(
                nickname = user!!.nickname,
                access_token = profileViewModel.state.profile!!.access_token
            )
        )
    }

    if (!state.isLoadingUser) {
        if (user != null) {
            PreviewFriend(
                navController = navController,
                user = user,
                showButtonAddFriend = showButtonAddFriend,
                sendInvite = sendInvite,
                inviteFriends = {
                    inviteFriend()
                },
                onClickCross = {
                    navController.navigate("team") {
                        popUpTo("team")
                        launchSingleTop = true
                    }

//                    navController.popBackStack()
                }
            )
        } else {
            PreviewEmptyFriend(
                navController = navController,
            )
        }
    } else {
        LoadingViewCenter()
    }
}