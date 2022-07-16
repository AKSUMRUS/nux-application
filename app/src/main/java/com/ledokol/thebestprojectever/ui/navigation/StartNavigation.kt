package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.content.Intent
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
import androidx.core.content.ContextCompat.startForegroundService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.ledokol.thebestprojectever.presentation.*
import com.ledokol.thebestprojectever.services.GamesStatistic
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.convertListApplicationToListStatusJSON
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.screens.*
import com.ledokol.thebestprojectever.ui.components.screens.friends.FriendScreen
import com.ledokol.thebestprojectever.ui.components.screens.friends.ListFriendsScreen
import com.ledokol.thebestprojectever.ui.components.screens.games.QuickGameScreen
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
    val contactsViewModel = hiltViewModel<ContactViewModel>()
    val profile = profileViewModel.profile.observeAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    var accessToken by remember {
        mutableStateOf("")
    }

    Log.e("Profile",profile.toString())

    LaunchedEffect(true){
//        gamesViewModel.shareGames(acc)
        gamesViewModel.getGames()
    }



    LaunchedEffect(true){
        if(accessToken!=""){
            Log.e("ShareGames","Start "+accessToken)
//            gamesViewModel.clearGames()
//            gamesViewModel.insertGames(
//                convertListApplicationToListGame(context, context.packageManager, getInstalledAppGamesList(context.packageManager))
//            )
//            gamesViewModel.shareGames(accessToken)
        }
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
        "share_screen" -> {
            bottomBarState.value = false
        }
        "request_permission_data" -> {
            bottomBarState.value = false
        }
        "request_permission_contacts" -> {
            bottomBarState.value = false
        }
        "contacts_list" -> {
            bottomBarState.value = false
        }
        "RequestContentPermission" -> {
            bottomBarState.value = false
        }
        "test" -> {
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
//        "test"
    }else if(profile.value!!.finish_register){
        accessToken = profile.value!!.access_token

        val intentService = Intent(context, MyService::class.java)
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startForegroundService(intentService)

        Log.e("ShareGames","Start "+accessToken)
        gamesViewModel.shareGames(
            convertListApplicationToListStatusJSON(context, context.packageManager, getInstalledAppGamesList(context.packageManager)),
            accessToken
        )

        "quick_game"
//        "test"
    } else {
        Log.e("profile",profile.value.toString())
        accessToken = profile.value!!.access_token

//        "test"
        "request_permission_data"
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
                        LoginScreen(
                            navController = navController,
                            viewModel = profileViewModel
                        )
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
                    composable("request_permission_data") {
                        userViewModel.accessToken = accessToken
                        RequestReadData(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel
                        )
                    }
                    composable("request_permission_contacts") {
                        RequestReadContacts(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                        )
                    }

                    composable("share_screen") {
                        ShareScreen(
                            navController = navController,
                        )
                    }

                    composable("contacts_list") {
                        ContactsList(
                            navController = navController,
                            contactsViewModel = contactsViewModel,
                            profileViewModel = profileViewModel,
                        )
                    }
                    composable("test") {
                        Test(
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
                            navController = navController,
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
                }
            )
        }
    }
}
