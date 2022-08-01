package com.ledokol.thebestprojectever.ui.components.screens.games

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.TitleQuickGame
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInListQuickGame


@Composable
fun ChooseFriendsForGame(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel
){
    val state = userViewModel.state
    val context = LocalContext.current

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

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

    LaunchedEffect(key1 = true, block = {
        userViewModel.clearSelectedUser()
    })

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
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            if (state.isLoading || gamesViewModel.state.game==null) {
                LoadingView()
            } else {
                LazyColumn(
                    content = {
                        item(
                        ){
                            Row(
                                modifier = Modifier.padding(top = 100.dp, bottom = 20.dp)
                            ) {
                                if(gamesViewModel.state.game!!.icon_large!=null){
                                    AsyncImage(
                                        model = gamesViewModel.state.game!!.icon_large,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(height = 100.dp, width = 100.dp)
                                    )
                                }

                                LazyRow(
                                    content = {
                                        items(userViewModel.state.clickedUsers){ user ->
                                            AsyncImage(
                                                model = user.profile_pic,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .size(height = 64.dp, width = 64.dp)
                                                    .align(CenterVertically)
                                                    .clip(RoundedCornerShape(32.dp))
                                                ,
                                                contentScale = ContentScale.Crop,
                                            )
                                        }
                                    },
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(CenterVertically)
                                    ,
                                )
                            }

                            TitleQuickGame(
                                step = 2,
                                title = stringResource(id = R.string.choose_crew_for_quick_game),
                                description = stringResource(id = R.string.description_crew),
                            )
                            ShowSearch(userViewModel = userViewModel)
                        }

                        items(state.users!!.size) { friend ->
                            val user = state.users!![friend]

//                            LaunchedEffect(state.clickedUsers){
                                FriendInListQuickGame(
                                    user = user,
                                    clicked = userViewModel.checkSelectedUser(user),
                                    onClick = {
                                        userViewModel.onEvent(UserEvent.SelectUser(user))
                                    })
//                            }
                        }
                        item{
                            Column(
                                modifier = Modifier.height(80.dp)
                            ){

                            }
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
            ButtonFull(
                text = stringResource(id = R.string.button_invite_friends),
                onClick = {
                    if(state.clickedUsers.size == 0){
                        Toast.makeText(context, "Добавьте друзей в команду", Toast.LENGTH_SHORT).show()
                        return@ButtonFull
                    }
                    val users: MutableList<String> = mutableListOf()
                    for(x in state.clickedUsers){
                        users.add(x.id)
                    }
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
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                ,
            )
        }

        BackToolbar(
            buttonBackClick = {
                navController.popBackStack()
            },
        )
    }
}