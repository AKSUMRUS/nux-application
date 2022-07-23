package com.ledokol.thebestprojectever.ui.components.screens.games

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.TitleQuickGame


@Composable
fun ChooseFriendsForGame(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel
){
    val state = userViewModel.state

    LaunchedEffect(key1 = true, block = {
        userViewModel.clearSelectedUser()
    })
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if(state.isRefreshing){
        Log.e("STATE",state.toString())
        userViewModel.onEvent(UserEvent.Refresh())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            if (state.isLoading) {
                LoadingView()
            } else {
                LazyColumn(
                    content = {
                        item{
                            TitleQuickGame(
                                step = 2,
                                title = stringResource(id = R.string.title_crew),
                                description = stringResource(id = R.string.description_crew),
                                modifier = Modifier.padding(top = 110.dp)
                            )
                            ShowSearch(userViewModel = userViewModel)
                        }

                        items(state.users!!.size) { friend ->
                            val user = state.users!![friend]

//                            LaunchedEffect(state.clickedUsers){
                                FriendInList(
                                    user = user,
                                    clicked = userViewModel.checkSelectedUser(user),
                                    onClick = {
                                        userViewModel.onEvent(UserEvent.SelectUser(user))
                                    })
//                            }
                        }
                    },
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
        ,
        ){
            ButtonPrimaryFull(
                text = stringResource(id = R.string.button_invite_friends),
                onClick = {
                    val users: MutableList<String> = mutableListOf()
                    for(x in state.clickedUsers){
                        users.add(x.id)
                    }
                    Log.e("F",profileViewModel.state.profile!!.access_token.toString())
                    Log.e("F",users.toString())
                    Log.e("F",gamesViewModel.state.toString())
                    profileViewModel.onEvent(
                        ProfileEvent.InviteFriends(
                            accessToken = profileViewModel.state.profile!!.access_token,
                            friends_ids = users.toList(),
                            app_id = gamesViewModel.state.game!!.id
                        )
                    )
                    navController.navigate("finish_inviting_friends"){
                        popUpTo("finish_inviting_friends")
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 20.dp)
                ,
            )
        }
    }
}