package com.ledokol.thebestprojectever.ui.components.screens.games

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.molecules.GameInList
import com.ledokol.thebestprojectever.ui.components.molecules.TitleQuickGame


const val TAG = "FIREBASE MESSAGING"

@Composable
fun QuickGameScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
){
    val games = gamesViewModel.state.games
    var token by remember{ mutableStateOf("")}

    LaunchedEffect(true){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val tokenGet = task.result

            token = tokenGet
            Log.e("myFirebaseToken", token)
            profileViewModel.onEvent(
                ProfileEvent.SetCurrentFirebaseToken(token, profileViewModel.state.profile!!.access_token)
            )
            Log.w(TAG, token)
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
    ) {
        games?.let { GridGames(
            it,
            navController,
            gamesViewModel = gamesViewModel
        ) }
    }
}

@Composable
fun GridGames(
    games: List<Game>,
    navController: NavController,
    gamesViewModel: GamesViewModel,
) {

    fun onClick(packageName: String){
        gamesViewModel.getGame(packageName)
        navController.navigate("choose_friends_quick_game"){
            popUpTo("choose_friends_quick_game"){
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 120.dp, start = 0.dp, end = 0.dp),
        modifier = Modifier
//            .padding(start = 20.dp, end = 20.dp)
        ,
    ) {
        item(
            span = { GridItemSpan(2) },
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                ,
            ){
                TitleQuickGame(
                    step = 1,
                    title = stringResource(id = R.string.choose_game),
                    description = stringResource(id = R.string.description_choose_game),
                )
            }
        }

        items(games) { game ->
            GameInList(
                packageName = game.android_package_name,
                name = game.name,
//                Временно!
                icon = game.icon_preview!!,
                iconLarge = game.icon_large!!,
                backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                onClick = { onClick(game.android_package_name) },
            )
        }
    }
}


fun log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}