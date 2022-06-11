package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.navigation.LoginAndSignUp
import com.ledokol.thebestprojectever.ui.navigation.NavigationGraph


@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController)}
    ) {
        NavigationGraph(navController = navController as NavHostController)
    }
}