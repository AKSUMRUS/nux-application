package com.nux.studio.dvor.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.navigation.NavController
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.ui.navigation.ScreenRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    LaunchedEffect(key1 = true) {
        delay(1500)
        navController.navigate(ScreenRoutes.START_REGISTRATION_SCREEN) {
            popUpTo(ScreenRoutes.START_REGISTRATION_SCREEN) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    var text: String = stringResource(id = R.string.hello_splash_screen, " ")
    if (profileViewModel.state.profile != null) {
        text = stringResource(
            id = R.string.hello_splash_screen,
            ", ${profileViewModel.state.profile!!.nickname}"
        )
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colors.primary)
    ) {
        HeadlineH4(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            fontWeight = W500,
        )

    }
}


//TeamGick
