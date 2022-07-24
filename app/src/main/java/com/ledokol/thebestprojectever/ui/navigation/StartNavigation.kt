package com.ledokol.thebestprojectever.ui.navigation


import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestprojectever.data.local.game.GamesEvent
import com.ledokol.thebestprojectever.internet.ConnectionState
import com.ledokol.thebestprojectever.internet.connectivityState
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.*
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.convertListApplicationToListStatusJSON
import com.ledokol.thebestprojectever.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.molecules.BottomNavigation
import com.ledokol.thebestprojectever.ui.components.screens.*
import com.ledokol.thebestprojectever.ui.components.screens.friends.FriendScreen
import com.ledokol.thebestprojectever.ui.components.screens.friends.ListFriendsScreen
import com.ledokol.thebestprojectever.ui.components.screens.games.ChooseFriendsForGame
import com.ledokol.thebestprojectever.ui.components.screens.games.FinishInvitingFriends
import com.ledokol.thebestprojectever.ui.components.screens.games.QuickGameScreen
import com.ledokol.thebestprojectever.ui.components.screens.profile.EditProfileScreen
import com.ledokol.thebestprojectever.ui.components.screens.profile.ProfileScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.LoginScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.SignUpScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.StartRegistrationScreen
import com.ledokol.thebestprojectever.ui.components.screens.registration.VerifyPhone
import com.ledokol.thebestprojectever.ui.theme.TheBestProjectEverTheme

@Composable
fun StartNavigation(
    navController: NavHostController,
    analytics: FirebaseAnalytics
) {
    val context: Context = LocalContext.current
    val userViewModel = hiltViewModel<UserViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    val gamesViewModel = hiltViewModel<GamesViewModel>()
    val userViewModel2 = hiltViewModel<UserViewModel>()
    val contactsViewModel = hiltViewModel<ContactViewModel>()
    val statusViewModel = hiltViewModel<StatusViewModel>()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    profileViewModel.onEvent(ProfileEvent.GetProfile)
    val profile = profileViewModel.state
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val packageManager = context.packageManager

    var accessToken by remember {
        mutableStateOf("")
    }

    LaunchedEffect(profile.profile){
        if(profile.profile!=null){
            val intentService = Intent(context, MyService::class.java)
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startForegroundService(intentService)
        }
    }

    fun pushGamesIcons(games: List<ApplicationInfo>){
        gamesViewModel.onEvent(GamesEvent.PushGamesIcons(
            games = games,
            packageManager = packageManager,
            accessToken = accessToken
        ))
    }

    Log.e("Profile",profile.toString())

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
        "not_internet" -> {
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
        "verify_phone" -> {
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

    val start: String = if(!isConnected){
        "not_internet"
    }else if(profile.profile==null){
        "splash_screen"
    } else if(profile.finish_register){
        accessToken = profile.profile.access_token

        Log.e("ShareGames","Start "+accessToken)
        gamesViewModel.clearGames()
        val games = getInstalledAppGamesList(context.packageManager)
        pushGamesIcons(games)
        gamesViewModel.shareGames(
            convertListApplicationToListStatusJSON(context, context.packageManager, games),
            accessToken
        )

        "quick_game"
    } else {
        Log.e("profile",profile.toString())
        accessToken = profile.profile.access_token
        gamesViewModel.clearGames()
        val games = getInstalledAppGamesList(context.packageManager)
        pushGamesIcons(games)
        gamesViewModel.shareGames(
            convertListApplicationToListStatusJSON(context, context.packageManager, games),
            accessToken
        )

        "request_permission_data"
    }

    fun logOpenScreenEvent(name: String){
        analytics.logEvent("open_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, name)
        }
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
                        logOpenScreenEvent("start_registration_screen")
                        StartRegistrationScreen(
                            navController = navController,
                        )
                    }
                    composable("login_screen") {
                        logOpenScreenEvent("login_screen")
                        LoginScreen(
                            navController = navController,
                            viewModel = profileViewModel
                        )
                    }
                    composable("signup_screen") {
                        logOpenScreenEvent("signup_screen")
                        SignUpScreen(
                            navController = navController,
                            viewModel = profileViewModel
                        )
                    }
                    composable("splash_screen") {
                        logOpenScreenEvent("splash_screen")
                        SplashScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                    }
                    composable("friend_screen") {
                        logOpenScreenEvent("friend_screen")
                        FriendScreen(
                            navController = navController,
                            userViewModel = userViewModel,
                            gamesViewModel = gamesViewModel,
                            profileViewModel = profileViewModel,
                        )
                    }
                    composable("finish_inviting_friends") {
                        logOpenScreenEvent("finish_inviting_friends")
                        FinishInvitingFriends(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                        )
                    }
                    composable("choose_friends_quick_game") {
                        logOpenScreenEvent("choose_friends_quick_game")
                        userViewModel2.accessToken = accessToken
                        ChooseFriendsForGame(
                            navController = navController,
                            userViewModel = userViewModel2,
                            profileViewModel = profileViewModel,
                            gamesViewModel = gamesViewModel,
                        )
                    }
                    composable("request_permission_data") {
                        logOpenScreenEvent("request_permission_data")
                        userViewModel.accessToken = accessToken
                        RequestReadData(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                        )
                    }
                    composable("request_permission_contacts") {
                        logOpenScreenEvent("request_permission_contacts")
                        RequestReadContacts(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            contactsViewModel = contactsViewModel,
                        )
                    }

                    composable("share_screen") {
                        logOpenScreenEvent("share_screen")
                        ShareScreen(
                            navController = navController,
                        )
                    }

                    composable("contacts_list") {
                        logOpenScreenEvent("contacts_list")
                        ContactsList(
                            navController = navController,
                            contactsViewModel = contactsViewModel,
                            profileViewModel = profileViewModel,
                        )
                    }

                    composable("edit_profile") {
                        logOpenScreenEvent("edit_profile")
                        EditProfileScreen(
                            profileViewModel = profileViewModel,
                            navController = navController,
                        )
                    }

                    composable("verify_phone") {
                        logOpenScreenEvent("verify_phone")
                        VerifyPhone(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                    }
                    composable("not_internet") {
                        logOpenScreenEvent("not_internet")
                        NotInternet(
                        )
                    }
                    composable(BottomNavItemMain.QuickGame.screen_route) {
                        logOpenScreenEvent(BottomNavItemMain.QuickGame.screen_route)
                        TheBestProjectEverTheme {
                            QuickGameScreen(
                                navController = navController,
                                gamesViewModel = gamesViewModel,
                                profileViewModel = profileViewModel,
                            )
                        }
                    }
                    composable(BottomNavItemMain.Profile.screen_route) {
                        logOpenScreenEvent(BottomNavItemMain.Profile.screen_route)
                        ProfileScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            gamesViewModel = gamesViewModel,
                            statusViewModel = statusViewModel
                        )
                    }
                    composable(BottomNavItemMain.Friends.screen_route) {
                        logOpenScreenEvent(BottomNavItemMain.Friends.screen_route)
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
