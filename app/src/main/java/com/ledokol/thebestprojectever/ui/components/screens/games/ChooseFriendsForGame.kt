package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Button
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.atoms.textfield.Search
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle
import com.ledokol.thebestprojectever.ui.components.molecules.TitleQuickGame


@Composable
fun ChooseFriendsForGame(
    navController: NavController,
    userViewModel: UserViewModel
){
    val state = userViewModel.state

    fun onClick(
        navController: NavController,
        nickname: String,
    ){
//        userViewModel.onEvent(UserEvent.GetFriendUser(nickname = nickname))
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
        }
    }

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
                                    name = user.nickname,
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
                                        navController.navigate("quick_game"){
                                            popUpTo("quick_game")
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