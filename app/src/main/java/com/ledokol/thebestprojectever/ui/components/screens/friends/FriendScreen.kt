package com.ledokol.thebestprojectever.ui.components.screens.friends

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.*
import com.ledokol.thebestprojectever.ui.components.molecules.friend.FriendTopBar

@Composable
fun FriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
){

    val user = userViewModel.state.friendUser
    val state = userViewModel.state

    if(user != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                )
//            .verticalScroll(rememberScrollState())
        ) {
            BackToolbar (
                buttonBackClick = {
                    navController.popBackStack()
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
//                contentPadding = PaddingValues(top = 0.dp, start = 20.dp, end = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                item(
                    span = { GridItemSpan(2) },
                ) {
                    FriendTopBar(user = user)
                }

                if(state.friendUser!!.status.current_app != null) {
                    item(
                        span = { GridItemSpan(2) },
                    ) {
                        HeadlineH5(
                            text = stringResource(id = R.string.latest_activity),
                            modifier = Modifier
                                .padding(start = 20.dp),
                            fontWeight = W700
                        )

                        state.friendUser.status.current_app.let { game ->
                            GameActivity(
                                packageName = game!!.android_package_name,
//                                ВРЕМЕННО
                                icon = "https://storage.yandexcloud.net/nux/icons/icon_preview/com.tencent.ig.png",
                                iconLarge = "https://storage.yandexcloud.net/nux/icons/image_wide/com.tencent.ig.png",
                                backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                                startTime = state.friendUser.status.dt_entered_app,
                                finishTime = state.friendUser.status.dt_last_update
                            )
                        }
                    }
                }


                if(state.games != null) {
                    item(
                        span = { GridItemSpan(2) },
                    ) {
                        Column() {
                            HeadlineH5(
                                text = stringResource(id = R.string.games),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .fillMaxWidth(),
                                fontWeight = W700
                            )
                        }
                    }

                    items(
                        state.games!!,
                    ) { game ->
                        GameInQuickGamesFriend(
                            packageName = game.android_package_name,
//                            icon = "https://storage.yandexcloud.net/nux/icons/icon_preview/"+game.android_package_name+".png",
//                            iconLarge = "https://storage.yandexcloud.net/nux/icons/icon_large/"+game.android_package_name+".png",
                            icon = "https://storage.yandexcloud.net/nux/icons/icon_preview/com.tencent.ig.png",
                            iconLarge = "https://storage.yandexcloud.net/nux/icons/icon_large/com.tencent.ig.png",
                            backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                            onClick = {
                                gamesViewModel.setSelectedGame(
                                    Game(
                                        android_package_name = game.android_package_name,
                                        name = game.name,
                                        id = game.id,
                                        category = game.category,
                                        icon_preview = game.icon_preview,
                                        image_wide = game.image_wide,
                                        icon_large = game.icon_large,
                                    )
                                )

                                userViewModel.clearSelectedUser()
                                userViewModel.insertSelectedUser(state.friendUser)
                                Log.e("getGame_ViewModel","inviteFriends: "+game.android_package_name+ " "+gamesViewModel.state.game.toString())
                                profileViewModel.onEvent(ProfileEvent.
                                InviteFriends(
                                    accessToken = profileViewModel.state.profile!!.access_token,
                                    friends_ids = listOf(state.friendUser.id),
                                    app_id = game.id,
                                ))
                                navController.navigate("finish_inviting_friends"){
                                    popUpTo("finish_inviting_friends")
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }


    }
}