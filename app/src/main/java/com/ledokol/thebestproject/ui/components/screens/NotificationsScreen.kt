package com.ledokol.thebestproject.ui.components.screens

import android.os.Handler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.notifications.NotificationEntity
import com.ledokol.thebestproject.data.local.notifications.NotificationsEvent
import com.ledokol.thebestproject.presentation.NotificationsViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.components.molecules.ScreenTitle
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendInNotification

@Composable
fun NotificationsScreen(
    notificationsViewModel: NotificationsViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel : UserViewModel,
    navController : NavController
) {

//    val state = notificationsViewModel.state
//    val usersList = state.friendInvites
//    val token = profileViewModel.state.profile?.access_token.toString()
//
//    LaunchedEffect(true) {
//        notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
//    }
//
//    val handler = Handler()
//    var runnable: Runnable? = null
//
//    fun onClick(
//        notificationEntity: NotificationEntity
//    ){
//        notificationsViewModel.onEvent(NotificationsEvent.AddFriend(token, notificationEntity))
//    }
//
//    LaunchedEffect(true){
//        runnable = Runnable {
//            notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
//            runnable?.let { handler.postDelayed(it, 5000) }
//        }
//
//        handler.postDelayed(runnable!!, 100)
//    }
//
//
//    LazyColumn(
//        modifier = Modifier
//            .padding(start = 20.dp, top = 110.dp,end = 20.dp)
//        ,
//        content = {
//            item {
//                ScreenTitle(
//                    name = stringResource(id = R.string.nav_notifications),
//                    description = "Тиммейты, которые хотят добавить тебя в друзья",
//                )
//            }
//
//            if(usersList!=null && usersList.size>0){
//                items(usersList!!.size) { friend ->
//                    val user = usersList[friend].from_user
//                    FriendInNotification(
//                        user = user,
//                        addFriend = {
//                            onClick(
//                                notificationEntity = usersList[friend]
//                            )
//                        })
//                }
//            }else{
//                item(){
//                    EmptyScreen(title = stringResource(id = R.string.empty_screen_title))
//                }
//            }
//        }
//    )

}