package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.screens.*
import com.ledokol.thebestprojectever.ui.components.screens.registration.StartRegistrationScreen

@Composable
fun StartNavigation(
    navController: NavHostController
) {
    val userViewModel = hiltViewModel<UserViewModel>()
    val viewModel = hiltViewModel<MainViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val profile = viewModel.profile.observeAsState(listOf())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

//    val context: Context = LocalContext.current
//    val packageManager = context.packageManager
////    , icon = (packageManager.getApplicationIcon(i.toString()) as BitmapDrawable).bitmap.asImageBitmap()
//
//    for (i in 0..10) {
//        gamesViewModel.insertGame(Game(gamePackage = i.toString(), name = i.toString()))
//    }

    when (navBackStackEntry?.destination?.route) {
        "signup_screen" -> {
            // Show BottomBar
            bottomBarState.value = false
        }
        "login_screen" -> {
            // Show BottomBar
            bottomBarState.value = false
        }
        "splash_screen" -> {
            // Show BottomBar
            bottomBarState.value = false
        }
        BottomNavItemMain.QuickGame.screen_route -> {
            // Show BottomBar
            bottomBarState.value = true
        }
        BottomNavItemMain.Profile.screen_route -> {
            // Hide BottomBar
            bottomBarState.value = true
        }
        BottomNavItemMain.Friends.screen_route -> {
            // Hide BottomBar
            bottomBarState.value = true
        }
    }

    val start: String = if(profile.value.isEmpty()){
//        ВРЕМЕННО
//        "splash_screen"
        "quick_game"
    }
    else{
        "quick_game"
//        BottomNavItemMain.QuickGame.screen_route
    }

    Scaffold(

        bottomBar = {
//            if(currentRoute(navController = navController) != "login_screen" && currentRoute(navController = navController) != "signup_screen") {
                BottomNavigation(navController = navController, bottomBarState = bottomBarState)
//            }
        },
    ) {
        innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController = navController, startDestination = start,
                    builder = {
                        composable("login_screen") {
                            LoginScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("signup_screen_first") {
                            SignUpScreenFirst(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("start_registration_screen") {
                            StartRegistrationScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("signup_screen_second") {
                            SignUpScreenSecond(
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
                            val userViewModel = hiltViewModel<UserViewModel>()
                            FriendScreen(userViewModel = userViewModel)
                        }
                        composable(BottomNavItemMain.QuickGame.screen_route) {
                            QuickGameScreen(viewModel = gamesViewModel)
                        }
                        composable(BottomNavItemMain.Profile.screen_route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                        composable(BottomNavItemMain.Friends.screen_route) {
                            val userViewModel = hiltViewModel<UserViewModel>()
                            ListFriendsScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                    })
            }
    }
}