package com.ledokol.thebestprojectever.ui.components.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import kotlinx.coroutines.delay

@Composable
fun FinishInvitingFriends(
    navController: NavController,
    gamesViewModel: GamesViewModel
) {
    LaunchedEffect(key1 = true){
        delay(2500)
        navController.navigate("quick_game"){
            popUpTo("quick_game"){
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
        ,

        ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            HeadlineH4(text = stringResource(id = R.string.finish_invite_friends),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                ,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
            )
            HeadlineH4(text = "В ${gamesViewModel.state.game!!.name}",
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 50.dp)
                ,
                fontWeight = FontWeight.W700,
            )

        }
    }
}
