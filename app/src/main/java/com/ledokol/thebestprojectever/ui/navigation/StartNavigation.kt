package com.ledokol.thebestprojectever.ui.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ledokol.thebestprojectever.ui.components.screens.MainScreen

@Composable
fun StartNavigation(

) {
    val navController = rememberNavController()

    val pref : SharedPreferences = LocalContext.current.getSharedPreferences("Data",
        Context.MODE_PRIVATE
    )

    val token: String? = pref.getString("token",null)


    val start: String = if(token == null){
        "login_and_signup"
    }
    else{
        "main"
    }

    NavHost(navController = navController, startDestination = start,
    builder = {
        composable("main", content = { MainScreen()})
        composable("login_and_signup", content = { LoginAndSignUp(navController2 = navController)})
    })
}