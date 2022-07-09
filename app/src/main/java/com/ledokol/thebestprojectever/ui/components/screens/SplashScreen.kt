package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.navigation.NavController
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    LaunchedEffect(key1 = true){
        delay(1500)
        navController.navigate("start_registration_screen"){
            popUpTo("start_registration_screen"){
                inclusive = true
            }
            launchSingleTop = true
        }
    }
    Column(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        HeadlineH4(text = stringResource(id = R.string.hello_splash_screen),
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ,
            fontWeight = W500,
        )

    }
}


//TeamGick
