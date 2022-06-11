package com.ledokol.thebestprojectever.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ledokol.thebestprojectever.ui.components.screens.LoginScreen
import com.ledokol.thebestprojectever.ui.components.screens.SignUpScreen

@Composable
fun LoginAndSignUp(navController2: NavController){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login_screen",
    builder = {
        composable("login_screen", content = { LoginScreen(navController = navController,navController2 = navController2) })
        composable("signup_screen", content = { SignUpScreen(navController = navController, navController2 = navController2) })
    })
}
