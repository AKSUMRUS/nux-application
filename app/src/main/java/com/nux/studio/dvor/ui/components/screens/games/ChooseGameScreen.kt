package com.nux.studio.dvor.ui.components.screens.games

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nux.studio.dvor.presentation.GamesViewModel
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.components.molecules.LoadingViewCenter
import com.nux.studio.dvor.ui.components.molecules.games.InstalledGamesList


const val TAG = "FIREBASE MESSAGING"

@Composable
fun ChooseGameScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel
) {
    val games = gamesViewModel.state.games

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .background(
                MaterialTheme.colors.background
            )
    ) {

        if (gamesViewModel.state.isLoading) {
            LoadingViewCenter()
        } else {
            InstalledGamesList(
                games!!,
                navController,
                gamesViewModel = gamesViewModel,
                profileViewModel = profileViewModel,
                userViewModel = userViewModel
            )
        }
    }
}

fun log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}