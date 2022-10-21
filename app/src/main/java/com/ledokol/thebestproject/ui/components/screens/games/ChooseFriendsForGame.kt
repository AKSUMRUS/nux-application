package com.ledokol.thebestproject.ui.components.screens.games

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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.games.TitleQuickGame
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendInListQuickGame
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes


@Composable
fun ChooseFriendsForGame(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel,
    analytics: FirebaseAnalytics
){
    val state = userViewModel.state
    val context = LocalContext.current

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    var textSearch by remember { mutableStateOf("") }

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
                                if(gamesViewModel.state.game?.icon_preview!=null){
                                    AsyncImage(
                                        model = gamesViewModel.state.game?.icon_preview,
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
                                    verticalAlignment = CenterVertically,
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
                            ShowSearch(
                                textSearch = textSearch,
                                onValueChange = {
                                    textSearch = it
                                }
                            )
                        }

                        items(state.users!!
                            .filter { it.nickname.contains(textSearch) }
                            .sortedBy { !it.status.in_app }
                        ) { friend ->
                            val user = friend

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

        if (!(state.isLoading || gamesViewModel.state.game==null)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
            ) {
                ButtonFull(
                    text = stringResource(id = R.string.button_invite_friends),
                    colorBackground = MaterialTheme.colors.secondary,
                    colorText = MaterialTheme.colors.onBackground,
                    onClick = {
                        if (state.clickedUsers.isEmpty()) {
                            Toast.makeText(context, "Добавьте друзей в команду", Toast.LENGTH_SHORT)
                                .show()
                            return@ButtonFull
                        }
                        val users: MutableList<String> = mutableListOf()
                        for (x in state.clickedUsers) {
                            users.add(x.id)
                        }
                        profileViewModel.onEvent(
                            ProfileEvent.InviteFriends(
                                accessToken = profileViewModel.state.profile!!.access_token,
                                friends_ids = users.toList(),
                                app_id = gamesViewModel.state.game!!.id
                            )
                        )
                        analytics.logEvent("open_screen") {
                            param(FirebaseAnalytics.Param.SCREEN_NAME, ScreenRoutes.FINISH_INVITING_FRIENDS)
                            param("from_what_screen", ScreenRoutes.CHOOSE_FRIENDS_QUICK_GAME)
                        }
                        navController.navigate(ScreenRoutes.FINISH_INVITING_FRIENDS) {
                            popUpTo(ScreenRoutes.FINISH_INVITING_FRIENDS)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 20.dp, end = 20.dp),
                )
            }
        }

        BackToolbar(
            buttonBackClick = {
                navController.popBackStack()
            },
        )
    }
}