package com.ledokol.thebestproject.ui.components.screens.friends

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.LoadingView
import com.ledokol.thebestproject.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.notifications.NotificationEntity
import com.ledokol.thebestproject.data.local.notifications.NotificationsEvent
import com.ledokol.thebestproject.presentation.NotificationsViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.ui.components.molecules.friend.*
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Friends(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    needUpdate: Boolean = true,
    notificationsViewModel: NotificationsViewModel,
){
    val state = userViewModel.state
    var isFindingNewFriends = false
    val shouldWork by remember {
        mutableStateOf(true)
    }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null
    val token = profileViewModel.state.profile?.access_token.toString()
    val usersList = notificationsViewModel.state.friendInvites

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if(isFindingNewFriends){
                if (event == Lifecycle.Event.ON_START) {
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                } else if (event == Lifecycle.Event.ON_STOP) {
                    runnable?.let { handler.removeCallbacks(it) }
                    userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
                }
            }
            else {
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

    fun onClick(
        navController: NavController,
        id: String,
    ){
        userViewModel.onEvent(UserEvent.GetFriendUser(id = id))
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
            launchSingleTop = true
        }
    }

    fun onClick(
        notificationEntity: NotificationEntity
    ){
        notificationsViewModel.onEvent(NotificationsEvent.AddFriend(token, notificationEntity))
    }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
    )

    var isSheetOpened by remember { mutableStateOf(false) }


    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide() // trigger the LaunchedEffect
        }
    }

    LaunchedEffect(true) {
        notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
    }


    var textSearch by remember { mutableStateOf("") }

    LaunchedEffect(true){
        runnable = Runnable {
            Log.e("FinishListFriends", "getFriends Okay $shouldWork")
            userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
            notificationsViewModel.onEvent(NotificationsEvent.GetFriendsRequests(token))
            runnable?.let { handler.postDelayed(it, 5000) }
        }

        handler.postDelayed(runnable!!, 100)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
    ) {

        ModalBottomSheetLayout(
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetState = modalBottomSheetState,

            sheetContent = {
                Scaffold(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                    ,

//                        .fillMaxSize()
//                        .padding(top = 100.dp)
//                        .border(3.dp, MaterialTheme.colors.primary, shape = RoundedCornerShape(18.dp))
                ){
                    InviteFriend(
                        navController = navController,
                        userViewModel = userViewModel,
                        profileViewModel = profileViewModel,
                    )
                }
            },
            sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
            sheetElevation = 10.dp,
//            sheetPeekHeight = 100.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                if (state.isLoading) {
                    LoadingView()
                } else {
                    LazyColumn(
                        content = {
                            item() {

                                Box(
                                    modifier = Modifier.height(90.dp)
                                ){

                                }

                                ButtonAddFriend(
                                    onClick = {
                                        coroutineScope.launch {
                                            isSheetOpened = true
                                            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }
                                )
                            }

                            if(usersList!=null && usersList.size>0){
                                item(){
                                    Column(){
                                        TitleFriends(text = stringResource(id = R.string.friend_requests))
                                        ShowSearch(
                                            userViewModel = userViewModel,
                                            textSearch = textSearch,
                                            onValueChange = {
                                                textSearch = it
                                            },
                                            modifier = Modifier.padding(top = 10.dp)
                                        )
                                    }
                                }

                                items(usersList!!.size) { friend ->
                                    val user = usersList[friend].from_user
                                    FriendInNotification(
                                        user = user,
                                        addFriend = {
                                            onClick(
                                                notificationEntity = usersList[friend]
                                            )
                                        },
                                        openFriend = {
                                            userViewModel.onEvent(UserEvent.GetFriendUser(user.id))
                                            navController.navigate("preview_friend"){
                                                popUpTo("preview_friend")
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }

                            item(){
                                Column(){
                                    TitleFriends(text = stringResource(id = R.string.my_friends),)

                                    ShowSearch(
                                        userViewModel = userViewModel,
                                        textSearch = textSearch,
                                        onValueChange = {
                                            textSearch = it
                                        },
                                        modifier = Modifier.padding(top = 0.dp)
                                    )

                                }
                            }

                            if(state.users!=null && state.users!!.size>0){
                                items(state.users!!.filter { it.nickname.contains(textSearch) }) { friend ->
                                    FriendInList(
                                        user = friend,
                                        onClick = {
                                            onClick(
                                                navController = navController,
                                                id = friend.id
                                            )
                                        })
                                }
                            }else{
                                item(){
                                    EmptyScreenFriend(title = stringResource(id = R.string.no_friends))
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

