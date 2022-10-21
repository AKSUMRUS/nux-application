package com.ledokol.thebestproject.ui.components.screens.games

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.components.molecules.games.GameInList
import com.ledokol.thebestproject.ui.components.molecules.games.InstalledGamesList
import com.ledokol.thebestproject.ui.components.molecules.games.TitleQuickGame
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes


const val TAG = "FIREBASE MESSAGING"

@Composable
fun QuickGameScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel,
){
    val games = gamesViewModel.state.games
    var token by remember{ mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .background(
                MaterialTheme.colors.background
            )
    ) {

        if(gamesViewModel.state.isLoading){
            LoadingView()
        }else{
            InstalledGamesList(
                games!!,
                navController,
                gamesViewModel = gamesViewModel
            )
        }
    }
}

fun log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}