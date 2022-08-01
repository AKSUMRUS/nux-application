package com.ledokol.thebestprojectever.ui.components.screens.games

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4
import kotlinx.coroutines.delay

@Composable
fun FinishInvitingFriends(
    navController: NavController,
    gamesViewModel: GamesViewModel,
    userViewModel: UserViewModel,
) {
    val context: Context = LocalContext.current

    fun clickFinish(){
        navController.navigate("quick_game"){
            popUpTo("quick_game"){
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun openGame(){
        val launchIntent = context.packageManager.getLaunchIntentForPackage(
            gamesViewModel.state.game!!.android_package_name)
        launchIntent?.let { context.startActivity(it) }
    }

//    LaunchedEffect(key1 = true){
//        delay(3000)
//    }

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
            HeadlineH4(text = context.resources.getQuantityString(
                R.plurals.finish_invite_friends,
                userViewModel.state.clickedUsers.size,
                userViewModel.state.clickedUsers.size,
            ),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(BottomCenter)
            ,

            verticalArrangement = Arrangement.Bottom,
        ) {
            ButtonFull(
                text = stringResource(id = R.string.open_game),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                ,
                onClick = {
                    openGame()
                },
                colorBackground = MaterialTheme.colors.onPrimary,
                colorText = MaterialTheme.colors.primary
            )

            ButtonBorder(
                text = stringResource(id = R.string.close),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                ,
                onClick = {
                    clickFinish()
                },
                colorBackground = MaterialTheme.colors.primary,
                colorText = MaterialTheme.colors.onPrimary,
                colorBorder = MaterialTheme.colors.onPrimary,
            )
        }
    }
}
