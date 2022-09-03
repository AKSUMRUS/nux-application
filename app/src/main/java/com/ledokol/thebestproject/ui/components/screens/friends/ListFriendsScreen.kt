package com.ledokol.thebestproject.ui.components.screens.friends

import android.os.Handler
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestproject.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.components.molecules.ScreenTitleFriends
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestproject.R

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    needUpdate: Boolean = true,
){
    val state = userViewModel.state
    var isFindingNewFriends = false
    var usersList by remember {
        mutableStateOf(state.users)
    }
    val shouldWork by remember {
        mutableStateOf(true)
    }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if(isFindingNewFriends){
                if (event == Lifecycle.Event.ON_START) {
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                } else if (event == Lifecycle.Event.ON_STOP) {
                    runnable?.let { handler.removeCallbacks(it) }
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                }
            }
            else {
                if (event == Lifecycle.Event.ON_START) {
                    userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
                } else if (event == Lifecycle.Event.ON_STOP) {
                    runnable?.let { handler.removeCallbacks(it) }
                    userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    fun onClick(
        navController: NavController,
        id: String,
    ){
        userViewModel.onEvent(UserEvent.GetFriendUser(id = id))
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
            launchSingleTop = true
        }
    }

    fun onClickShare(){
        navController.navigate("invite_friends"){
            popUpTo("invite_friends")
            launchSingleTop = true
        }
    }
    var textSearch by remember { mutableStateOf("") }

    //Поиск по всем пользователям
//    fun onClickFindFriend(){
//        if(!isFindingNewFriends) {
//            usersList = state.findNewFriendsList
//            Log.e("ListFriendsScreen", "onClickFindFriend $usersList")
//            userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
//            isFindingNewFriends = true
//        }
//        else{
//            usersList = state.users
//            Log.e("ListFriendsScreen", "onClickFindFriend $usersList")
//            userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
//            isFindingNewFriends = false
//        }
//    }

    LaunchedEffect(true){
        runnable = Runnable {
            Log.e("FinishListFriends", "getFriends Okay $shouldWork")
            userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
            runnable?.let { handler.postDelayed(it, 5000) }
        }

        handler.postDelayed(runnable!!, 100)
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
                                    ScreenTitleFriends(
                                        name = stringResource(id = R.string.nav_friends),
                                        modifier = Modifier.padding(top = 110.dp),
//                                        onFindFriendClick = {onClickFindFriend()}
                                    )
                                    ShowSearch(
                                        userViewModel = userViewModel,
                                        textSearch = textSearch,
                                        onValueChange = {
                                            textSearch = it
                                        }
                                    )
                                    ButtonBorder(
                                        onClick = {onClickShare()},
                                        text = stringResource(id = R.string.add_friends),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 20.dp)
                                        ,
                                        padding = 2.dp
                                    )
                                }

                                if(state.users!=null && state.users!!.size>0){
                                    items(state.users!!.filter { it.nickname.contains(textSearch) }) { friend ->
                                        FriendInList(
                                            user = friend,
                                            onClick = {
                                                onClick(
                                                    navController = navController,
                                                    id = friend.id
                                                )
                                            })
                                    }
                                }else{
                                    item(){
                                        EmptyScreen(title = stringResource(id = R.string.empty_screen_title))
                                    }
                                }
                            },
                        )
                }
            }
        }
}

