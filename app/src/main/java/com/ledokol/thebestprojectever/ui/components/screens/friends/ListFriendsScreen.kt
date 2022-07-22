package com.ledokol.thebestprojectever.ui.components.screens.friends

import android.content.pm.PackageManager
import android.os.Handler
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.services.GamesStatistic
import com.ledokol.thebestprojectever.services.getApplicationCategory
import com.ledokol.thebestprojectever.services.getApplicationLabel
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.Search
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle
import com.ledokol.thebestprojectever.ui.components.screens.checkPermissionReadData

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    needUpdate: Boolean = true,
){
    val state = userViewModel.state
    var shouldWork by remember {
        mutableStateOf(true)
    }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
            }else if (event == Lifecycle.Event.ON_STOP) {
                runnable?.let { handler.removeCallbacks(it) }
                userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
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
        navController.navigate("share_screen"){
            popUpTo("share_screen")
            launchSingleTop = true
        }
    }


    LaunchedEffect(true){
        runnable = Runnable {
            Log.e("FinishListFriends", "getUsers Okay $shouldWork")
            userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
            runnable?.let { handler.postDelayed(it, 5000) }
        }

        handler.postDelayed(runnable!!, 10)
//        updateFriends(
//            handler = handler,
//            userViewModel = userViewModel,
//            shouldWork = shouldWork,
//        )
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
                                    ShowSearch(userViewModel = userViewModel)
                                    ButtonPrimary(
                                        onClick = {onClickShare()},
                                        text = stringResource(id = R.string.share_with_friends),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 20.dp)
                                        ,
                                        padding = 2.dp
                                    )
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


fun updateFriends(
    handler: Handler,
    userViewModel: UserViewModel,
    shouldWork: Boolean,
){


}