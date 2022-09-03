package com.ledokol.thebestproject.ui.components.molecules.friend

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
import androidx.compose.ui.layout.ContentScale.Companion.Crop
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
import com.ledokol.thebestproject.data.local.user.User
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.data.local.user.UserState
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

@Composable
fun PreviewFriend(
    navController: NavController,
    user: User,
    showButtonAddFriend: Boolean,
    sendInvite: Boolean,
    inviteFriends: () -> Unit,
    onClickCross: () -> Unit,
){

    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                )
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
//            .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                user.profile_pic,
                contentDescription = null,
                modifier = Modifier
                    .size(height = 150.dp, width = 150.dp)
                    .padding(bottom = 10.dp)
                ,
                alignment = Alignment.Center,
                contentScale = Crop,
            )

            HeadlineH2(
                text = user.name,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                ,
                fontWeight = Bold,
            )

            HeadlineH6(
                text = "@${user.nickname}",
                modifier = Modifier
                    .padding(bottom = 20.dp)
                ,
                color = MaterialTheme.colors.secondary,

                )


            if(showButtonAddFriend){
                ButtonFull(
                    text = if(!sendInvite) "Добавить в друзья" else "Приглашение отправлено",
                    onClick = {
                        if(!sendInvite){
                            inviteFriends()
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                    ,
                    padding = PaddingValues(start = 35.dp, end = 35.dp, top = 10.dp, bottom = 10.dp),
                    colorBackground = if(sendInvite) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
                    colorText = if(sendInvite) MaterialTheme.colors.onSurface else MaterialTheme.colors.onPrimary,
                )
            }
        }

        Cross(
            onClick = {
              onClickCross()
            }
        ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}