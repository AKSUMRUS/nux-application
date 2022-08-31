package com.ledokol.thebestproject.ui.components.screens.friends

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestproject.ui.components.atoms.textfields.ShowSearch
import com.ledokol.thebestproject.ui.components.molecules.EmptyScreen
import com.ledokol.thebestproject.ui.components.molecules.ScreenTitleFriends
import com.ledokol.thebestproject.ui.components.molecules.friend.FriendInList
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.ui.components.molecules.friend.ButtonAddFriend
import com.ledokol.thebestproject.ui.components.screens.InviteFriend
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Friends(
    navController: NavController,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    needUpdate: Boolean = true,
){
    val state = userViewModel.state
    var isFindingNewFriends = false
    val shouldWork by remember {
        mutableStateOf(true)
    }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val handler = Handler()
    var runnable: Runnable? = null

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

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },

    )
    var isSheetOpened by remember { mutableStateOf(false) }


//    fun onClickShare(){
//        isSheetOpened = true
//        modalBottomSheetState.show()
//    }

    BackHandler {
        coroutineScope.launch {
            modalBottomSheetState.hide() // trigger the LaunchedEffect
        }
    }

    LaunchedEffect(
        key1 = modalBottomSheetState.currentValue,
    ) {
//        if (modalBottomSheetState.targetValue == ModalBottomSheetValue.HalfExpanded) {
//            coroutineScope.launch {
//                modalBottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
//            }
//        }else if(modalBottomSheetState.targetValue == ModalBottomSheetValue.Expanded){
//            coroutineScope.launch {
//                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
//            }
//        }
    }

//    LaunchedEffect(modalBottomSheetState.currentValue) {
//        when (modalBottomSheetState.currentValue) {
//            ModalBottomSheetValue.Hidden -> {
//                when {
////                    isSheetOpened -> parent.removeView(composeView)
////                    !isSheetOpened -> {
////                        isSheetOpened = true
////                        modalBottomSheetState.show()
////                    }
//                }
//            }
//            else -> {
////                Log.i(TAG, "Bottom sheet ${...} state")
//            }
//        }
//    }

    var textSearch by remember { mutableStateOf("") }

    //Поиск по всем пользователям
//    fun onClickFindFriend(){
//        if(!isFindingNewFriends) {
//            usersList = state.findNewFriendsList
//            Log.e("Friends", "onClickFindFriend $usersList")
//            userViewModel.onEvent(UserEvent.OnSearchQueryChangeFindFriend(""))
//            isFindingNewFriends = true
//        }
//        else{
//            usersList = state.users
//            Log.e("Friends", "onClickFindFriend $usersList")
//            userViewModel.onEvent(UserEvent.OnSearchQueryChange(""))
//            isFindingNewFriends = false
//        }
//    }

    LaunchedEffect(true){
        runnable = Runnable {
            Log.e("FinishListFriends", "getFriends Okay $shouldWork")
            userViewModel.onEvent(UserEvent.Refresh(shouldReload = false))
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
                            item {
                                ScreenTitleFriends(
                                    name = stringResource(id = R.string.nav_friends),
                                    modifier = Modifier.padding(top = 110.dp),
//                                        onFindFriendClick = {onClickFindFriend()}
                                )
                                ShowSearch(
                                    userViewModel = userViewModel,
                                    textSearch = textSearch,
                                    onValueChange = {
                                        textSearch = it
                                    }
                                )

                                ButtonAddFriend(
                                    onClick = {
                                        coroutineScope.launch {
                                            isSheetOpened = true
                                            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
//                                            modalBottomSheetState.show()
//                                            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }
                                )
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
                                    EmptyScreen(title = stringResource(id = R.string.empty_screen_title))
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

