package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic.Companion.convertListApplicationToListGame
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestprojectever.ui.components.screens.*
import com.ledokol.thebestprojectever.ui.components.screens.registration.LoginScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.SignUpScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.StartRegistrationScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartNavigation(
    navController: NavHostController,
    analytics: FirebaseAnalytics
) {
    val context: Context = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()
    val viewModel = hiltViewModel<MainViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val profile = viewModel.profile.observeAsState(listOf())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    var accessToken = ""

    LaunchedEffect(true){
//        userViewModel.getUsers()
        gamesViewModel.insertGames(
            convertListApplicationToListGame(context.packageManager, getInstalledAppGamesList(context.packageManager))
        )
    }

    when (navBackStackEntry?.destination?.route) {
        "signup_screen" -> {
            bottomBarState.value = false
        }
        "login_screen" -> {
            bottomBarState.value = false
        }
        "start_registration_screen" -> {
            bottomBarState.value = false
        }
        "signup_screen_second" -> {
            bottomBarState.value = false
        }
        "splash_screen" -> {
            bottomBarState.value = false
        }
        "friend_screen" -> {
            bottomBarState.value = false
        }
        BottomNavItemMain.QuickGame.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Profile.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Friends.screen_route -> {
            bottomBarState.value = true
        }
    }

    val start: String = if(profile.value.isEmpty()){
        "splash_screen"
//        "test"
    }
    else{
        accessToken = profile.value[0].access_token
        BottomNavItemMain.QuickGame.screen_route
//        "test"
    }

    Log.e("ACCESS",accessToken)

    Scaffold(

        bottomBar = {
            BottomNavigation(navController = navController, bottomBarState = bottomBarState)
        },
    ) {
        innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController = navController, startDestination = start,
                    builder = {
                        composable("start_registration_screen") {
                            StartRegistrationScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("login_screen") {
                            LoginScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("signup_screen") {
                            SignUpScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("splash_screen") {
                            SplashScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("friend_screen") {
                            FriendScreen(userViewModel = userViewModel)
                        }
                        composable(BottomNavItemMain.QuickGame.screen_route) {
                            QuickGameScreen(
                                viewModel = gamesViewModel,
                                userViewModel = userViewModel
                            )
                        }
                        composable(BottomNavItemMain.Profile.screen_route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                        composable(BottomNavItemMain.Friends.screen_route) {
                            userViewModel.accessToken = accessToken
                            ListFriendsScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }

                        composable("test"){
                            TestScreen()
                        }
                    })
            }
    }
}