package com.ledokol.thebestproject.ui.components.screens.friends

import android.content.Context
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.CurrentApp
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.Cross
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.alertdialogs.AlertDialogShow
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.*
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.*
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendTopBar
import com.ledokol.thebestproject.ui.components.molecules.friend.PreviewEmptyFriend
import com.ledokol.thebestproject.ui.components.molecules.friend.PreviewFriend

@Composable
fun PreviewFriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
){
    val user = userViewModel.state.friendUser
    val state = userViewModel.state
    var sendInvite by remember { mutableStateOf(false) }
    var showButtonAddFriend by remember{ mutableStateOf(true)}

    LaunchedEffect(key1 = true, block ={
        userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
    })

    LaunchedEffect(userViewModel.state.users, user){
        val users = userViewModel.state.users
        if(user!=null && user.id == profileViewModel.state.profile!!.id){
            showButtonAddFriend = false
        }
        if(users!=null&&user!=null&&user.id!=profileViewModel.state.profile!!.id){
            Log.e("UsersFriendScreen", users.toString())
            for(userInList in users){
                if(user!!.id == userInList.id){
                    showButtonAddFriend = false
                }
            }
        }
    }

    fun inviteFriend(){
        sendInvite = true
        userViewModel.onEvent(UserEvent.AddFriend(nickname = user!!.nickname, access_token = profileViewModel.state.profile!!.access_token))
    }

    if(!state.isLoading) {
        if(user != null){
            PreviewFriend(
                navController = navController,
                user = user,
                showButtonAddFriend = showButtonAddFriend,
                sendInvite = sendInvite,
                inviteFriends = {
                    inviteFriend()
                },
                onClickCross = {
                    navController.popBackStack()
                }
            )
        }else{
            PreviewEmptyFriend(
                navController = navController,
            )
        }
    }else{
        LoadingView()
    }
}