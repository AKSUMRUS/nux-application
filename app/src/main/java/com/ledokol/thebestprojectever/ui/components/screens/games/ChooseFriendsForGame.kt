package com.ledokol.thebestprojectever.ui.components.screens.games

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.TitleQuickGame
import com.ledokol.thebestprojectever.ui.components.screens.friends.showSearch


@Composable
fun ChooseFriendsForGame(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel
){
    val state = userViewModel.state

    if(state.isRefreshing){
        Log.e("STATE",state.toString())
        userViewModel.onEvent(UserEvent.Refresh)
    }
    Column(
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
                            showSearch(userViewModel = userViewModel)
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
                        item{
                            Box(
                                modifier = Modifier.fillMaxWidth(),
//                                contentAlignment = Alignment.End,
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
                                        profileViewModel.inviteFriends(accessToken = profileViewModel.state.profile!!.access_token, friends_ids = users.toList(), app_id = gamesViewModel.state.game!!.android_package_name)
                                        navController.navigate("finish_inviting_friends"){
                                            popUpTo("finish_inviting_friends")
                                            launchSingleTop = true
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(bottom = 20.dp)
                                    ,
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}