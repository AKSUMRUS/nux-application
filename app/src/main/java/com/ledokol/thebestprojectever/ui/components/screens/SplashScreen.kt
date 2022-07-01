package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    LaunchedEffect(key1 = true){
        delay(1000)
        navController.navigate("start_registration_screen"){
            popUpTo("start_registration_screen"){
                inclusive = true
            }
            launchSingleTop = true
        }
    }
    Column(
    ) {
        Body1(text = "Splash Screen", modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
        )

    }
}