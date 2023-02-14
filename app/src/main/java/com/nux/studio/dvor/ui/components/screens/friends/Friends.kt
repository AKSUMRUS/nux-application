package com.nux.studio.dvor.ui.components.screens.friends

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.nux.dvor.ui.components.atoms.buttons.ButtonWithChangeableColor
import com.nux.dvor.ui.components.atoms.textfields.ShowSearch
import com.nux.dvor.ui.components.molecules.friend.*
import com.nux.dvor.ui.components.screens.friends.InviteFriend
import com.nux.studio.dvor.R
import com.nux.studio.dvor.data.local.notifications.NotificationEntity
import com.nux.studio.dvor.data.local.notifications.NotificationsEvent
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.data.local.user.UserEvent
import com.nux.studio.dvor.presentation.NotificationsViewModel
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.components.molecules.LoadingViewCenter
import com.nux.studio.dvor.ui.components.molecules.friend.*
import com.nux.studio.dvor.ui.navigation.ScreenRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Friends(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    notificationsViewModel: NotificationsViewModel,
) {
    val state = userViewModel.state
    val isFindingNewFriends = false
    val shouldWork by remember {
        mutableStateOf(true)
    }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null
    val token = profileViewModel.state.profile?.access_token.toString()
    val usersList = notificationsViewModel.state.friendInvites
    val context = LocalContext.current

    var isInviteButtonClicked by remember {
        mutableStateOf(state.inviteFriends.isNotEmpty())
    }

    val recommendedFriends = state.recommendedFriends

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (isFindingNewFriends) {
                if (event == Lifecycle.Event.ON_START) {
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                } else if (event == Lifecycle.Event.ON_STOP) {
                    runnable?.let { handler.removeCallbacks(it) }
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                }
            } else {
                if (event == Lifecycle.Event.ON_START) {
                    userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
                } else if (event == Lifecycle.Event.ON_STOP) {
                    runnable?.let { handler.removeCallbacks(it) }
                    userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    fun onAddFriend(friend: User) {
        userViewModel.onEvent(UserEvent.SelectUser(friend))
        Log.e("Friends_ADD", state.inviteFriends.toString())
    }

    fun onClickFriend(
        navController: NavController,
        id: String,
    ) {
        userViewModel.onEvent(UserEvent.GetFriendUser(id = id))
        navController.navigate(ScreenRoutes.FRIEND_SCREEN) {
            popUpTo(ScreenRoutes.FRIEND_SCREEN)
            launchSingleTop = true
        }
    }

    fun onClick(
        notificationEntity: NotificationEntity
    ) {
        notificationsViewModel.onEvent(NotificationsEvent.AddFriend(notificationEntity))
    }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
    )

    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide() // trigger the LaunchedEffect
        }
    }

    LaunchedEffect(true) {
        notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
    }

    var textSearch by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        runnable = Runnable {
            Log.e("FinishListFriends", "getFriends Okay $shouldWork")
            userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
            notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
            runnable?.let { handler.postDelayed(it, 5000) }
        }

        handler.postDelayed(runnable!!, 100)
    }

    LaunchedEffect(state.inviteFriends) {
        isInviteButtonClicked = state.inviteFriends.isNotEmpty()
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetState = modalBottomSheetState,
        sheetContent = {
            InviteFriend(
                navController = navController,
                userViewModel = userViewModel,
                profileViewModel = profileViewModel,
            )
        },
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetElevation = 10.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    MaterialTheme.colors.background
                )
        ) {
            if (state.isLoading) {
                LoadingViewCenter()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(bottom = 65.dp),
                    content = {
                        item {
                            TitleFriends(
                                text = stringResource(id = R.string.invite_to_game_title),
                                modifier = Modifier
                                    .padding(top = 30.dp, bottom = 20.dp)
                            )
                            ButtonAddFriend(
                                onClick = {
                                    coroutineScope.launch {
                                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                            )
                        }

                        if (usersList != null && usersList.isNotEmpty()) {
                            item {
                                Column {
                                    TitleFriends(text = stringResource(id = R.string.friend_requests))
                                }
                            }

                            items(usersList.size) { friend ->
                                val user = usersList[friend].from_user
                                FriendInNotification(
                                    user = user,
                                    rejectFriend = {
                                        userViewModel.onEvent(UserEvent.RejectInvite(user.id))
                                    },
                                    addFriend = { onClick(notificationEntity = usersList[friend]) },
                                    openFriend = {
                                        userViewModel.onEvent(UserEvent.GetFriendUser(user.id))
                                        navController.navigate(ScreenRoutes.PREVIEW_FRIEND) {
                                            popUpTo(ScreenRoutes.PREVIEW_FRIEND)
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }

                        item {
                            Column {
                                ShowSearch(
                                    textSearch = textSearch,
                                    onValueChange = {
                                        textSearch = it
                                    },
                                    modifier = Modifier.padding(top = 0.dp)
                                )

                            }
                        }

                        if (state.users != null && state.users!!.isNotEmpty()) {
                            val arrayFriends =
                                state.users!!.filter { it.nickname.contains(textSearch) }
                            if (arrayFriends.isEmpty()) {
                                userViewModel.onEvent(UserEvent.GetUserByNickname(textSearch))
                                val user = if (state.friendUser?.nickname == textSearch) {
                                    state.friendUser
                                } else {
                                    null
                                }
                                if (user != null) {
                                    item {
                                        AddFriendInSearch(
                                            user = user,
                                            addFriend = {
                                                userViewModel.onEvent(UserEvent.AddFriend(user.id))
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.invite_sent),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            },
                                            openFriend = {
                                                userViewModel.onEvent(UserEvent.GetFriendUser(user.id))
                                                navController.navigate(ScreenRoutes.PREVIEW_FRIEND) {
                                                    popUpTo(ScreenRoutes.PREVIEW_FRIEND)
                                                    launchSingleTop = true
                                                }
                                            }
                                        )
                                    }
                                }
                            } else {
                                items(arrayFriends.filter { state.inviteFriends.contains(it.id) }
                                    .sortedBy { if (it.status.in_app) -1 else 1 }) { friend ->
                                    FriendInList(
                                        user = friend,
                                        onClickFriend = {
                                            onClickFriend(
                                                navController = navController,
                                                id = friend.id
                                            )
                                        },
                                        onAdd = {
                                            onAddFriend(friend)
                                        },
                                        clickedAdd = true
                                    )
                                }
                                items(arrayFriends.filter { !state.inviteFriends.contains(it.id) }
                                    .sortedBy { if (it.status.in_app) -1 else 1 }) { friend ->
                                    FriendInList(
                                        user = friend,
                                        onClickFriend = {
                                            onClickFriend(
                                                navController = navController,
                                                id = friend.id
                                            )
                                        },
                                        onAdd = {
                                            onAddFriend(friend)
                                        },
                                        clickedAdd = false
                                    )
                                }
                            }
                        } else if (recommendedFriends != null) {
                            item {
                                EmptyScreenFriend(
                                    title = stringResource(id = R.string.recommended_friends_main),
                                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                                )
                            }
                            items(recommendedFriends.size) { id ->
                                val friend = recommendedFriends[id]
                                AddFriendInSearch(
                                    user = friend,
                                    addFriend = {
                                        userViewModel.onEvent(UserEvent.AddFriend(friend.id))
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.invite_sent),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        recommendedFriends.remove(friend)
                                    },
                                    openFriend = {

                                    })
                            }
                        } else {
                            item {
                                EmptyScreenFriend(title = stringResource(id = R.string.no_friends))
                            }
                            userViewModel.onEvent(UserEvent.GetRecommendedFriends)
                        }
                    },
                )
            }
            ButtonWithChangeableColor(
                isClicked = isInviteButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                onClick = {
                    navController.navigate(ScreenRoutes.CHOOSE_GAMES)
                },
                text1 = stringResource(id = R.string.invite_to_game),
                text2 = stringResource(id = R.string.invite_to_game),
                color1 = MaterialTheme.colors.surface,
                color1Text = MaterialTheme.colors.secondaryVariant,
                color2 = MaterialTheme.colors.secondary,
                color2Text = MaterialTheme.colors.onSurface,
            )
        }
    }
}