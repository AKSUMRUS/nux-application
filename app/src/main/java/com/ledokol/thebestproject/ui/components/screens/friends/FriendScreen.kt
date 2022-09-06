package com.ledokol.thebestproject.ui.components.screens.friends

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.data.local.user.CurrentApp
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.alertdialogs.AlertDialogShow
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar
import com.ledokol.thebestproject.ui.components.molecules.*
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendTopBar
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes

@Composable
fun FriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
    analytics: FirebaseAnalytics,
){

    val user = userViewModel.state.friendUser
    val state = userViewModel.state
    val context: Context = LocalContext.current
    var openDialog by remember{ mutableStateOf(false)}
    var selectedGame by remember{ mutableStateOf("")}
    var sendInvite by remember { mutableStateOf(false) }
    var showButtonAddFriend by remember{ mutableStateOf(true)}

    LaunchedEffect(userViewModel.state.users, user){
        val users = userViewModel.state.users
        if(user!=null && user.id == profileViewModel.state.profile!!.id){
            showButtonAddFriend = false
        }
        if(users!=null&&user!=null&&user.id!=profileViewModel.state.profile!!.id){
            Log.e("UsersFriendScreen", users.toString())
            for(userInList in users){
                if(user.id == userInList.id){
                    showButtonAddFriend = false
                }
            }
        }
    }

    fun inviteFriend(game: CurrentApp){
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
        userViewModel.insertSelectedUser(state.friendUser!!)
        Log.e("getGame_ViewModel","inviteFriends: "+game.android_package_name+ " "+gamesViewModel.state.game.toString())
        profileViewModel.onEvent(ProfileEvent.
        InviteFriends(
            accessToken = profileViewModel.state.profile!!.access_token,
            friends_ids = listOf(state.friendUser.id),
            app_id = game.id,
        ))
        analytics.logEvent("open_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, ScreenRoutes.FINISH_INVITING_FRIENDS)
            param("from_what_screen",ScreenRoutes.FRIEND_SCREEN)
        }
        navController.navigate(ScreenRoutes.FINISH_INVITING_FRIENDS){
            popUpTo(ScreenRoutes.FINISH_INVITING_FRIENDS)
            launchSingleTop = true
        }
    }

    if(user!=null&&!state.isLoadingUser) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                )
//            .verticalScroll(rememberScrollState())
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
//                contentPadding = PaddingValues(top = 0.dp, start = 20.dp, end = 20.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ){
                item(
                    span = { GridItemSpan(2) },
                ) {
                    FriendTopBar(user = user)
                }

                if(state.friendUser!!.status.app != null) {
                    item(
                        span = { GridItemSpan(2) },
                    ) {

                        state.friendUser.status.app?.let { game ->
                            if(game.android_package_name!=""){
                                var textActivity: String = "";
                                textActivity = if(user.status.in_app){
                                    stringResource(id = R.string.game_now_friend)
                                }else{
                                    stringResource(id = R.string.last_game_friend)
                                }

                                HeadlineH5(
                                    text = textActivity,
                                    modifier = Modifier
                                        .padding(start = 20.dp),
                                    fontWeight = W700
                                )

                                GameActivity(
                                    packageName = game.android_package_name,
                                    iconPreview = game.icon_preview,
                                    imageWide = game.image_wide,
                                    in_app = state.friendUser.status.in_app,
                                    startTime = state.friendUser.status.dt_entered_app,
                                    finishTime = state.friendUser.status.dt_leaved_app
                                )
                            }
                        }
                    }
                }


                if(state.games != null && state.games!!.isNotEmpty()) {
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
                            icon = game.icon_preview,
                            iconLarge = game.icon_large,
                            backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                            onClick = {
                                selectedGame = game.android_package_name
                                openDialog = true
                            }
                        )
                    }
                }
            }

            BackToolbar (
                buttonBackClick = {
//                    navController.popBackStack()
                    navController.navigate("team"){
                        popUpTo("team")
                        launchSingleTop = true
                    }
                },
            )
        }

        AlertDialogShow(
            openDialog = openDialog,
            label = stringResource(id = R.string.invite_one_friend_alert_dialog_title),
            description = stringResource(id = R.string.invite_one_friend_alert_dialog_description),
            buttonTextYes = stringResource(id = R.string.invite),
            buttonTextNo = stringResource(id = R.string.cancel),
            onAction = {
                var game: CurrentApp = CurrentApp()
                for(game_local in state.games!!){
                    if(game_local.android_package_name == selectedGame){
                        game = game_local
                        break
                    }
                }
                inviteFriend(game)
                       },
            onClose = { openDialog = false; selectedGame = "" }
        )
    }else{
        LoadingView()
    }
}