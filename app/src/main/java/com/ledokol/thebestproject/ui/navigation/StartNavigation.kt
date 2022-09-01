package com.ledokol.thebestproject.ui.navigation


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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestproject.data.local.game.GamesEvent
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.internet.ConnectionState
import com.ledokol.thebestproject.internet.connectivityState
import com.ledokol.thebestproject.presentation.*
import com.ledokol.thebestproject.services.GamesStatistic.Companion.convertListApplicationToListStatusJSON
import com.ledokol.thebestproject.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestproject.services.MyService
import com.ledokol.thebestproject.ui.components.molecules.BottomNavigation
import com.ledokol.thebestproject.ui.components.molecules.profile.GamesListProfile
import com.ledokol.thebestproject.ui.components.screens.*
import com.ledokol.thebestproject.ui.components.screens.friends.AddFriendByName
import com.ledokol.thebestproject.ui.components.screens.friends.FindFriendByName
import com.ledokol.thebestproject.ui.components.screens.friends.FriendScreen
import com.ledokol.thebestproject.ui.components.screens.friends.ListFriendsScreen
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
    val statusViewModel = hiltViewModel<StatusViewModel>()
    val notificationsViewModel = hiltViewModel<NotificationsViewModel>()
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
        BottomNavItemMain.Notifications.screen_route -> {
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
        Log.e(TAG,"openScreenRegister ${profile.toString()}")
        accessToken = profile.profile.access_token
        gamesViewModel.clearGames()
        val games = getInstalledAppGamesList(context.packageManager)
        pushGamesIcons(games)
        gamesViewModel.shareGames(
            convertListApplicationToListStatusJSON(context, context.packageManager, games),
            accessToken
        )

        "request_permission_data"
    } else {
        accessToken = profile.profile.access_token

        Log.e("ShareGames", "Start $accessToken ${userViewModel.state.openScreen}")
        gamesViewModel.clearGames()
        val games = getInstalledAppGamesList(context.packageManager)
        pushGamesIcons(games)
        gamesViewModel.shareGames(
            convertListApplicationToListStatusJSON(context, context.packageManager, games),
            accessToken
        )

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
                        userViewModel2.accessToken = accessToken
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
                        userViewModel.accessToken = accessToken
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
                        )
                        logOpenScreenEvent("edit_profile")
                    }

                    composable("not_internet") {
                        NotInternet(
                        )
                        logOpenScreenEvent("not_internet")
                    }
                    composable("find_friend_by_name"){
                        FindFriendByName(
                            userViewModel = userViewModel,
                            navController = navController
                        )
                        logOpenScreenEvent("find_friend_by_name")
                    }
                    composable("add_friend_by_name"){
                        AddFriendByName(
                            userViewModel = userViewModel,
                            navController = navController
                        )
                        logOpenScreenEvent("add_friend_by_name")
                    }
                    composable("games"){
                        GamesListProfile(
                            gamesViewModel = gamesViewModel,
                            navController = navController,
                        )
                        logOpenScreenEvent("games")
                    }
                    composable(BottomNavItemMain.QuickGame.screen_route) {
                        TheBestProjectEverTheme {
                            QuickGameScreen(
                                navController = navController,
                                gamesViewModel = gamesViewModel,
                                profileViewModel = profileViewModel,
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
                        logOpenScreenEvent(BottomNavItemMain.Friends.screen_route)
                        userViewModel.accessToken = accessToken
                        ListFriendsScreen(
                            navController = navController,
                            userViewModel = userViewModel
                        )
                        logOpenScreenEvent(BottomNavItemMain.Friends.screen_route)
                    }
                    composable(BottomNavItemMain.Notifications.screen_route) {
                        userViewModel.accessToken = accessToken
                        NotificationsScreen(
                            notificationsViewModel = notificationsViewModel,
                            userViewModel = userViewModel,
                            profileViewModel = profileViewModel,
                            navController = navController,
                        )
                        logOpenScreenEvent(BottomNavItemMain.Notifications.screen_route)
                    }
                }
            )
        }
    }
}
