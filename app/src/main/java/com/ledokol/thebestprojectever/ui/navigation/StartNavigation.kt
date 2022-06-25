package com.ledokol.thebestprojectever.ui.navigation


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.screens.*

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun StartNavigation(
    viewModel: MainViewModel
) {
    val profile = viewModel.profile.observeAsState(listOf())

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

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
                            FriendScreen(viewModel = viewModel)
                        }
                        composable(BottomNavItemMain.QuickGame.screen_route) {
                            QuickGameScreen()
                        }
                        composable(BottomNavItemMain.Profile.screen_route) {
                            ProfileScreen(viewModel = viewModel)
                        }
                        composable(BottomNavItemMain.Friends.screen_route) {
                            ListFriendsScreen(navController = navController)
                        }
                    })
            }
    }
}