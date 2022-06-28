package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitile

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel
){
    val state = userViewModel.state

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
        .gradientBackground(
            listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
            angle = 105f
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, start = 20.dp, end = 20.dp)
        ) {
            ScreenTitile(name = stringResource(id = R.string.nav_friends))
            Search(state = state,
            viewModel = userViewModel)
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
            viewModel.onEvent(UserEvent.OnSearchQueryChange(text)),
            value = textInSearch.value,
            onValueChange = {textInSearch.value = it},
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 17.sp),
            leadingIcon = { Icon(Icons.Filled.Search, null, tint = MaterialTheme.colors.onBackground) },
            maxLines = 1,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.onBackground.copy(alpha = 0.36f), RoundedCornerShape(16.dp)),
            placeholder = { Text(text = "Bun") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                placeholderColor = MaterialTheme.colors.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray
            )

        })
    }
}