package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.presentation.StatusViewModel
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.Button
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.molecules.GameInQuickGames
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic.Companion.convertListApplicationToListGame
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle
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
//    val statusViewModel = hiltViewModel<StatusViewModel>()
    val statusViewModel: StatusViewModel = hiltViewModel<StatusViewModel>()
//    val statusViewModel = StatusViewModel::class.java
    val viewModel = hiltViewModel<MainViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val profile = viewModel.profile.observeAsState(listOf())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    var accessToken = ""

//    statusViewModel.setS
//    statusViewModel.se
//    val intentService = Intent(context, MyService::class.java)
//    intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//    context.startForegroundService(intentService)

    LaunchedEffect(true){
//        userViewModel.getUsers()
        gamesViewModel.clearGames()
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
        "choose_friends_quick_game" -> {
            bottomBarState.value = true
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
                NavHost(
                    navController = navController,
                    startDestination = start,
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
                            FriendScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                        composable("choose_friends_quick_game") {
                            userViewModel.accessToken = accessToken
                            ChooseFriendsForGame(
                                navController = navController,
                                userViewModel = userViewModel,
                            )
                        }
                        composable(BottomNavItemMain.QuickGame.screen_route) {
                            QuickGame(
                                navController = navController,
                                viewModel = gamesViewModel
                            )
                        }
                        composable(BottomNavItemMain.Profile.screen_route) {
                            ProfileScreen(
                                mainViewModel = viewModel,
                                gamesViewModel = gamesViewModel,
                            )
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
                    }
                )
            }
    }
}
