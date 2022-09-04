package com.ledokol.thebestproject.ui.navigation


import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestproject.data.local.game.GamesEvent
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.internet.ConnectionState
import com.ledokol.thebestproject.internet.connectivityState
import com.ledokol.thebestproject.presentation.*
import com.ledokol.thebestproject.services.GamesStatistic.Companion.convertListApplicationToListStatusJSON
import com.ledokol.thebestproject.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestproject.services.MyService
import com.ledokol.thebestproject.ui.components.molecules.BottomNavigation
import com.ledokol.thebestproject.ui.components.molecules.AddByQrCode
import com.ledokol.thebestproject.ui.components.screens.*
import com.ledokol.thebestproject.ui.components.screens.friends.*
import com.ledokol.thebestproject.ui.components.screens.games.ChooseFriendsForGame
import com.ledokol.thebestproject.ui.components.screens.games.FinishInvitingFriends
import com.ledokol.thebestproject.ui.components.screens.games.QuickGameScreen
import com.ledokol.thebestproject.ui.components.screens.permissions.RequestReadData
import com.ledokol.thebestproject.ui.components.screens.profile.EditProfileScreen
import com.ledokol.thebestproject.ui.components.screens.profile.ProfileScreen
import com.ledokol.thebestproject.ui.components.screens.registration.LoginScreen
import com.ledokol.thebestproject.ui.components.screens.registration.SignUpScreen
import com.ledokol.thebestproject.ui.components.screens.registration.StartRegistrationScreen
import com.ledokol.thebestproject.ui.theme.TheBestProjectEverTheme

const val TAG = "StartNavigation"

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
    val notificationsViewModel = hiltViewModel<NotificationsViewModel>()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    profileViewModel.onEvent(ProfileEvent.GetProfile)
    val profile = profileViewModel.state
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    LaunchedEffect(true){
        if(profile.profile!=null){
            updateGames(context, gamesViewModel)
        }
    }

    LaunchedEffect(profile.profile){
        if(profile.profile!=null){
            val intentService = Intent(context, MyService::class.java)
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startForegroundService(intentService)

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(com.ledokol.thebestproject.ui.components.screens.games.TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val tokenGet = task.result

                if(profileViewModel.state.profile != null){
                    Log.e("myFirebaseToken", "$tokenGet ${profileViewModel.state.profile!!.access_token}")
                    profileViewModel.setCurrentFirebaseToken(tokenGet, profileViewModel.state.profile!!.access_token)
                }
            })

        }
    }


    Log.e(TAG,"profile: ${userViewModel.state.openScreen.toString()} $profile")

    when (navBackStackEntry?.destination?.route) {
        BottomNavItemMain.QuickGame.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Profile.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Friends.screen_route -> {
            bottomBarState.value = true
        }
        else -> {
            bottomBarState.value = false
        }
    }

    val start: String = if(!isConnected){
        "not_internet"
    }else if(profile.profile==null){
        "splash_screen"
    } else if(!profile.finish_register){
        Log.e(TAG,"openScreenRegister $profile")
        updateGames(context, gamesViewModel)

        "request_permission_data"
    } else {
        Log.e(TAG, "openScreen ${userViewModel.state.openScreen}")
        val openScreen = userViewModel.state.openScreen

        if(openScreen!=null){
            Log.e(TAG, "openScreenFinish $openScreen")
            "$openScreen"
        }else{
            "quick_game"
        }
    }

    fun logOpenScreenEvent(name: String){
        analytics.logEvent("open_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, name)
        }
    }

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
                    composable(BottomNavItemMain.QuickGame.screen_route) {
                        TheBestProjectEverTheme {
                            QuickGameScreen(
                                navController = navController,
                                gamesViewModel = gamesViewModel,
                            )
                        }
                        logOpenScreenEvent(BottomNavItemMain.QuickGame.screen_route)
                    }
                    composable(BottomNavItemMain.Profile.screen_route) {
                        ProfileScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(BottomNavItemMain.Profile.screen_route)
                    }
                    composable(BottomNavItemMain.Friends.screen_route) {
                        Friends(
                            navController = navController,
                            userViewModel = userViewModel,
                            profileViewModel = profileViewModel,
                            notificationsViewModel = notificationsViewModel,
                        )
                        logOpenScreenEvent(BottomNavItemMain.Friends.screen_route)
                    }
                    composable(BottomNavItemMain.Notifications.screen_route) {
                        NotificationsScreen(
                            notificationsViewModel = notificationsViewModel,
                            userViewModel = userViewModel,
                            profileViewModel = profileViewModel,
                            navController = navController,
                        )
                        logOpenScreenEvent(BottomNavItemMain.Notifications.screen_route)
                    }
                    composable("start_registration_screen") {
                        StartRegistrationScreen(
                            navController = navController,
                        )
                        logOpenScreenEvent("start_registration_screen")
                    }
                    composable("login_screen") {
                        LoginScreen(
                            navController = navController,
                            profileViewModel = profileViewModel
                        )
                        logOpenScreenEvent("login_screen")
                    }
                    composable("signup_screen") {
                        SignUpScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            usersViewModel = userViewModel,
                        )
                        logOpenScreenEvent("signup_screen")
                    }
                    composable("splash_screen") {
                        SplashScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent("splash_screen")
                    }
                    composable("friend_screen") {
                        FriendScreen(
                            navController = navController,
                            userViewModel = userViewModel,
                            gamesViewModel = gamesViewModel,
                            profileViewModel = profileViewModel,
                            analytics = analytics
                        )
                        logOpenScreenEvent("friend_screen")
                    }
                    composable("finish_inviting_friends") {
                        FinishInvitingFriends(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                        )
                    }
                    composable("choose_friends_quick_game") {
                        ChooseFriendsForGame(
                            navController = navController,
                            userViewModel = userViewModel2,
                            profileViewModel = profileViewModel,
                            gamesViewModel = gamesViewModel,
                            analytics = analytics
                        )
                        logOpenScreenEvent("choose_friends_quick_game")
                    }
                    composable("request_permission_data") {
                        RequestReadData(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent("request_permission_data")
                    }
                    composable("invite_friends") {
                        InviteFriend(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent("invite_friends")
                    }
                    composable("qr_code_profile") {
                        AddByQrCode(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent("qr_code_profile")
                    }
                    composable("add_by_nickname") {
                        AddByNickname(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent("add_by_nickname")
                    }
                    composable("preview_friend") {
                        PreviewFriendScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent("preview_friend")
                    }

                    composable("contacts_list") {
                        ContactsScreen(
                            navController = navController,
                            contactsViewModel = contactsViewModel,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent("contacts_list")
                    }

                    composable("edit_profile") {
                        EditProfileScreen(
                            profileViewModel = profileViewModel,
                            navController = navController,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent("edit_profile")
                    }

                    composable("not_internet") {
                        NotInternet(
                        )
                        logOpenScreenEvent("not_internet")
                    }
                }
            )
        }
    }
}


fun updateGames(context: Context, gamesViewModel: GamesViewModel){
    gamesViewModel.clearGames()
    val games = getInstalledAppGamesList(context.packageManager)
    pushGamesIcons(games, gamesViewModel,context.packageManager)
    gamesViewModel.shareGames(
        convertListApplicationToListStatusJSON(context, context.packageManager, games),
    )
}

fun pushGamesIcons(games: List<ApplicationInfo>, gamesViewModel: GamesViewModel, packageManager: PackageManager){
    gamesViewModel.onEvent(GamesEvent.PushGamesIcons(
        games = games,
        packageManager = packageManager,
    ))
}
