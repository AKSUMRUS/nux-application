package com.ledokol.thebestproject.ui.components.molecules.games

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GamesEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes

@Composable
fun InstalledGamesList(
    games: List<Game>,
    navController: NavController,
    gamesViewModel: GamesViewModel,
) {

    fun onClick(packageName: String){
        gamesViewModel.getGame(packageName)
        navController.navigate(ScreenRoutes.CHOOSE_FRIENDS_QUICK_GAME){
            popUpTo(ScreenRoutes.CHOOSE_FRIENDS_QUICK_GAME){
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 120.dp, start = 0.dp, end = 0.dp),
        modifier = Modifier
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

        if(games.isEmpty()){
//            gamesViewModel.onEvent()
            item(span = { GridItemSpan(2) }){
                EmptyScreen(title = stringResource(id = R.string.not_online_games))
            }
        }else {
            items(games) { game ->
                GameInList(
                    packageName = game.android_package_name,
                    name = game.name,
                    icon = game.icon_preview!!,
                    onClick = { onClick(game.android_package_name) },
                )
            }
        }
    }
}