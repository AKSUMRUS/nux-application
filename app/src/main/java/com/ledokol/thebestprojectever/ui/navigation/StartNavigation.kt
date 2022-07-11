package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.StatusViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.convertListApplicationToListGame
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
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
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val profile = profileViewModel.profile.observeAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    var accessToken = ""


    LaunchedEffect(true){
        gamesViewModel.getGames()
    }

//    val intentService = Intent(context, MyService::class.java)
//    intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//    context.startForegroundService(intentService)

    LaunchedEffect(true){
//        userViewModel.getUsers()
        gamesViewModel.clearGames()
        gamesViewModel.insertGames(
            convertListApplicationToListGame(context, context.packageManager, getInstalledAppGamesList(context.packageManager))
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
        "finish_inviting_friends" -> {
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

    val start: String = if(profile.value==null){
        "splash_screen"
    } else{
        Log.e("profile",profile.value.toString())
        accessToken = profile.value!!.access_token
        "request_permission"
//        BottomNavItemMain.QuickGame.screen_route
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
                        StartRegistrationScreen(
                            navController = navController,
                        )
                    }
                    composable("login_screen") {
                        LoginScreen(navController = navController, viewModel = profileViewModel)
                    }
                    composable("signup_screen") {
                        SignUpScreen(
                            navController = navController,
                            viewModel = profileViewModel
                        )
                    }
                    composable("splash_screen") {
                        SplashScreen(
                            navController = navController,
                            viewModel = profileViewModel
                        )
                    }
                    composable("friend_screen") {
                        FriendScreen(
                            navController = navController,
                            userViewModel = userViewModel,
                            gamesViewModel = gamesViewModel,
                        )
                    }
                    composable("finish_inviting_friends") {
                        FinishInvitingFriends(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                        )
                    }
                    composable("choose_friends_quick_game") {
                        val userViewModel2 = hiltViewModel<UserViewModel>()
                        userViewModel2.accessToken = accessToken
                        ChooseFriendsForGame(
                            navController = navController,
                            userViewModel = userViewModel2
                        )
                    }
                    composable("request_permission") {
                        RequestPermission(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                        )
                    }

                    composable("request_permission") {
                        RequestPermission(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                        )
                    }
                    composable(BottomNavItemMain.QuickGame.screen_route) {
                        QuickGameScreen(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            profileViewModel = profileViewModel,
                        )
                    }
                    composable(BottomNavItemMain.Profile.screen_route) {
                        ProfileScreen(
                            profileViewModel = profileViewModel,
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
