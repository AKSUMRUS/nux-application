package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.TextFieldTrailingIcon
import com.ledokol.thebestprojectever.ui.components.atoms.textfield.Search
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel
){
    val state = userViewModel.state

    if(state.isRefreshing){
        userViewModel.onEvent(UserEvent.Refresh)
    }


    fun onClick(
        navController: NavController,
        nickname: String,
    ){
        userViewModel.onEvent(UserEvent.GetFriendUser(nickname = nickname))
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
            launchSingleTop = true
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            MaterialTheme.colors.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, start = 20.dp, end = 20.dp)
        ) {
            ScreenTitile(name = stringResource(id = R.string.nav_friends))
            Search(
                placeholder = stringResource(id = R.string.enter_nickname_search),
                text = textSearch,
                icon = Icons.Default.Close,
                onValueChange = {
                    setTextSearch(it)
                    userViewModel.onEvent(UserEvent.OnSearchQueryChange(textSearch))
                },
                trailingButtonClick = {
                    setTextSearch("")
                },
                modifier = Modifier
//                    .padding(bottom = 20.dp)
//                    .fillMaxWidth()
//                    .background(
//                        MaterialTheme.colors.onBackground.copy(alpha = 0.36f),
//                        RoundedCornerShape(16.dp)
//                    )
            )
            LazyColumn(
                content = {
                    items(state.users!!.size) { friend ->
                        val user = state.users[friend]
                        FriendInList(
                            name = user.nickname,
                            onClick = { onClick(navController = navController,nickname = user.nickname) })
                    }
                },
            )
        }
    }
}

@Composable
@Preview
fun ListFriendsScreen_preview(){
    val state = mutableListOf<User>(
        User(nickname = "@Pashka"),
        User(nickname = "@Gordeyka"),
        User(nickname = "@Rita"),
        User(nickname = "@Daniilka"),
    )
    LazyColumn(
        content = {
            items(state.size) { friend ->
                val user = state[friend]
                FriendInList(name = user.nickname, onClick = {

                })
            }
        },
    )
}

@Composable
fun Search(
    state: UserState,
    viewModel: UserViewModel
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var text by remember { mutableStateOf("") }
        TextField(label = "Enter @nickname", text = text, onValueChange = {
            text = it
            viewModel.onEvent(UserEvent.OnSearchQueryChange(query = text))
        })
    }
}