package com.ledokol.thebestprojectever.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ledokol.thebestprojectever.ui.components.screens.FriendsScreen
import com.ledokol.thebestprojectever.ui.components.screens.ProfileScreen
import com.ledokol.thebestprojectever.ui.components.screens.QuickGameScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    // Какой путь какой экран должен запустить

    NavHost(navController = navController, startDestination = BottomNavItemMain.QuickGame.screen_route){
        composable(BottomNavItemMain.QuickGame.screen_route){
            QuickGameScreen()
        }
        composable(BottomNavItemMain.Profile.screen_route){
            ProfileScreen()
        }
        composable(BottomNavItemMain.Friends.screen_route){
            FriendsScreen()
        }
    }

}