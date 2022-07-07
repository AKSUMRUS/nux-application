package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.data.repository.StatusRepository
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.textfield.Search
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel
){
    val state = userViewModel.state
    val isRefreshing = state.isRefreshing

//    userViewModel.getUsers()

    fun onClick(
        navController: NavController,
        id: String,
    ){
        userViewModel.onEvent(UserEvent.GetFriendUser(id = id))
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
//            launchSingleTop = true
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
                                item {
                                    ScreenTitle(
                                        name = stringResource(id = R.string.nav_friends),
                                        modifier = Modifier.padding(top = 110.dp),
                                    )
                                    showSearch(userViewModel = userViewModel)
                                }

                                items(state.users!!.size) { friend ->
                                    val user = state.users!![friend]
                                    FriendInList(
                                        user = user,
                                        onClick = {
                                            onClick(
                                                navController = navController,
                                                id = user.id
                                            )
                                        })
                                }
                            },
                        )
                }
            }
        }
}


@Composable
@Preview
fun ListFriendsScreen_preview(){
    val state = mutableListOf<User>(
//        User(nickname = "@Pashka"),
//        User(nickname = "@Gordeyka"),
//        User(nickname = "@Rita"),
//        User(nickname = "@Daniilka"),
    )
    LazyColumn(
        content = {
            items(state.size) { friend ->
                val user = state[friend]
                FriendInList(user = user, onClick = {

                })
            }
        },
    )
}

@Composable
fun showSearch(
    userViewModel: UserViewModel
){
    var textSearch by remember { mutableStateOf("") }
    Search(
        placeholder = stringResource(id = R.string.enter_nickname_search),
        text = textSearch,
        icon = Icons.Default.Close,
        onValueChange = {
            textSearch = it
            userViewModel.onEvent(UserEvent.OnSearchQueryChange(textSearch))
        },
        trailingButtonClick = {
            textSearch = ""
        },
        modifier = Modifier
    )
}