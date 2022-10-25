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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GamesEvent
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes

@Composable
fun InstalledGamesList(
    games: List<Game>,
    navController: NavController,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel
) {

    fun onClick(packageName: String){
        gamesViewModel.getGame(packageName)
        profileViewModel.onEvent(
            ProfileEvent.InviteFriends(
                accessToken = profileViewModel.state.profile!!.access_token,
                friends_ids = userViewModel.state.inviteFriends.toList(),
                app_id = gamesViewModel.state.game!!.id
            )
        )
        navController.navigate(ScreenRoutes.FINISH_INVITING_FRIENDS) {
            popUpTo(ScreenRoutes.FINISH_INVITING_FRIENDS)
            launchSingleTop = true
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 80.dp, start = 0.dp, end = 0.dp),
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
                    title = stringResource(id = R.string.choose_game),
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