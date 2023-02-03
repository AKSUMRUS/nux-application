package com.ledokol.thebestproject.ui.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.core.dependencyprovider.DependencyProvider
import com.ledokol.thebestproject.core.feature.api.FeatureApi
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.internet.ConnectionState
import com.ledokol.thebestproject.internet.connectivityState
import com.ledokol.thebestproject.presentation.*
import com.ledokol.thebestproject.services.GamesStatistic.Companion.convertListApplicationToListStatusJSON
import com.ledokol.thebestproject.services.GamesStatistic.Companion.getInstalledAppGamesList
import com.ledokol.thebestproject.services.MyService
import com.ledokol.thebestproject.ui.components.molecules.AddByQrCode
import com.ledokol.thebestproject.ui.components.molecules.BottomNavigation
import com.ledokol.thebestproject.ui.components.screens.ContactsScreen
import com.ledokol.thebestproject.ui.components.screens.NotInternet
import com.ledokol.thebestproject.ui.components.screens.NotificationsScreen
import com.ledokol.thebestproject.ui.components.screens.SplashScreen
import com.ledokol.thebestproject.ui.components.screens.friends.*
import com.ledokol.thebestproject.ui.components.screens.games.ChooseGameScreen
import com.ledokol.thebestproject.ui.components.screens.games.FinishInvitingFriends
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

    fun NavGraphBuilder.register(
        featureApi: FeatureApi,
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        featureApi.registerGraph(
            navGraphBuilder = this,
            navController = navController,
            modifier = modifier
        )
    }

    LaunchedEffect(profile.profile) {
        if (profile.profile != null) {
            updateGames(context, gamesViewModel)
        }
    }

    LaunchedEffect(profile.profile) {
        if (profile.profile != null) {
            val intentService = Intent(context, MyService::class.java)
            intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startForegroundService(intentService)

            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        com.ledokol.thebestproject.ui.components.screens.games.TAG,
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }

                val tokenGet = task.result

                if (profileViewModel.state.profile != null) {
                    Log.e(
                        "myFirebaseToken",
                        "$tokenGet ${profileViewModel.state.profile!!.access_token}"
                    )
                    profileViewModel.setCurrentFirebaseToken(
                        tokenGet,
                        profileViewModel.state.profile!!.access_token
                    )
                }
            })

        }
    }

    Log.e(TAG, "profile: ${userViewModel.state.openScreen.toString()} $profile")

    when (navBackStackEntry?.destination?.route) {
        BottomNavItemMain.Profile.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Friends.screen_route -> {
            bottomBarState.value = true
        }
        BottomNavItemMain.Chats.screen_route -> {
            bottomBarState.value = true
        }
        else -> {
            bottomBarState.value = false
        }
    }

    if (!isConnected) {
        Toast.makeText(context, stringResource(id = R.string.error_no_internet), Toast.LENGTH_LONG)
            .show()
    }

    val start: String = if (profile.profile == null) {
        ScreenRoutes.SPLASH_SCREEN
    } else if (!profile.finish_register) {
        Log.e(TAG, "openScreenRegister $profile")

        ScreenRoutes.REQUEST_PERMISSION_DATA
    } else {

        Log.e(TAG, "openScreen ${userViewModel.state.openScreen}")
        val openScreen = userViewModel.state.openScreen

        if (openScreen != null) {
            Log.e(TAG, "openScreenFinish $openScreen")
            "$openScreen"
        } else {
            BottomNavItemMain.Friends.screen_route
        }
    }

    fun logOpenScreenEvent(name: String) {
        analytics.logEvent("open_screen") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, name)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, bottomBarState = bottomBarState)
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = start,
                builder = {
                    composable(ScreenRoutes.CHOOSE_GAMES) {
                        TheBestProjectEverTheme {
                            ChooseGameScreen(
                                navController = navController,
                                gamesViewModel = gamesViewModel,
                                profileViewModel = profileViewModel,
                                userViewModel = userViewModel,
                            )
                        }
                        logOpenScreenEvent(ScreenRoutes.CHOOSE_GAMES)
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
                    composable(ScreenRoutes.START_REGISTRATION_SCREEN) {
                        StartRegistrationScreen(
                            navController = navController,
                        )
                        logOpenScreenEvent(ScreenRoutes.START_REGISTRATION_SCREEN)
                    }
                    composable(ScreenRoutes.LOGIN_SCREEN) {
                        LoginScreen(
                            navController = navController,
                            profileViewModel = profileViewModel
                        )
                        logOpenScreenEvent(ScreenRoutes.LOGIN_SCREEN)
                    }
                    composable(ScreenRoutes.SIGNUP_SCREEN) {
                        SignUpScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            usersViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.SIGNUP_SCREEN)
                    }
                    composable(ScreenRoutes.SPLASH_SCREEN) {
                        SplashScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.SPLASH_SCREEN)
                    }
                    composable(ScreenRoutes.FRIEND_SCREEN) {
                        FriendScreen(
                            navController = navController,
                            userViewModel = userViewModel,
                            gamesViewModel = gamesViewModel,
                            profileViewModel = profileViewModel,
                            analytics = analytics
                        )
                        logOpenScreenEvent(ScreenRoutes.FRIEND_SCREEN)
                    }
                    composable(ScreenRoutes.FINISH_INVITING_FRIENDS) {
                        FinishInvitingFriends(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.FINISH_INVITING_FRIENDS)
                    }
                    composable(ScreenRoutes.REQUEST_PERMISSION_DATA) {
                        RequestReadData(
                            navController = navController,
                            gamesViewModel = gamesViewModel,
                            userViewModel = userViewModel,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.REQUEST_PERMISSION_DATA)
                    }
                    composable(ScreenRoutes.INVITE_FRIENDS) {
                        InviteFriend(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.INVITE_FRIENDS)
                    }
                    composable(ScreenRoutes.QR_CODE_PROFILE) {
                        AddByQrCode(
                            navController = navController,
                            profileViewModel = profileViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.QR_CODE_PROFILE)
                    }
                    composable(ScreenRoutes.ADD_BY_NICKNAME) {
                        AddByNickname(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.ADD_BY_NICKNAME)
                    }
                    composable(ScreenRoutes.PREVIEW_FRIEND) {
                        PreviewFriendScreen(
                            navController = navController,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.PREVIEW_FRIEND)
                    }

                    composable(ScreenRoutes.CONTACTS_LIST) {
                        ContactsScreen(
                            navController = navController,
                            contactsViewModel = contactsViewModel,
                            profileViewModel = profileViewModel,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.CONTACTS_LIST)
                    }

                    composable(ScreenRoutes.EDIT_PROFILE) {
                        EditProfileScreen(
                            profileViewModel = profileViewModel,
                            navController = navController,
                            userViewModel = userViewModel,
                        )
                        logOpenScreenEvent(ScreenRoutes.EDIT_PROFILE)
                    }

                    composable(ScreenRoutes.NO_INTERNET) {
                        NotInternet()
                        logOpenScreenEvent(ScreenRoutes.NO_INTERNET)
                    }

                    register(
                        featureApi = DependencyProvider.chatFeature(),
                        navController = navController
                    )
                }
            )
        }
    }
}

fun updateGames(context: Context, gamesViewModel: GamesViewModel) {
    gamesViewModel.clearGames()
    val games = getInstalledAppGamesList(context.packageManager)
    gamesViewModel.shareGames(
        convertListApplicationToListStatusJSON(context, context.packageManager, games),
        context = context
    )
}
