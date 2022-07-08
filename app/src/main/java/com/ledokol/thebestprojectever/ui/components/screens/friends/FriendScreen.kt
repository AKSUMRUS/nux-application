package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.UserInformationProfile

@Composable
fun FriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
){

    val state = userViewModel.state

    Log.e("FRiend",state.toString())
    if(state.friendUser != null) {

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



            UserInformationProfile(
                name = state.friendUser.nickname,
                profile = false,
            )
        }


    }
}