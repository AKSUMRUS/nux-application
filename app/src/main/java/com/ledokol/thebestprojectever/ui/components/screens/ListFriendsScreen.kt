package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserViewModel
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList

@Composable
fun ListFriendsScreen(
    navController: NavController,
    userViewModel: UserViewModel
){
    val state = userViewModel.state

    fun onClick(navController: NavController){
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
            launchSingleTop = true
        }
    }

    LazyColumn(
        content = {
            items(state.users!!.size) { friend ->
                val user = state.users[friend]
                FriendInList(name = user.nickname, onClick = { onClick(navController = navController) })
            }
        },
    )
}
//){

//    val swipeRefreshState = rememberSwipeRefreshState(
//        isRefreshing = viewModel.state.isRefreshing
//    )

//    Log.e("USERS",state.toString())
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(state.users!!.size) { i ->
//                val user = state.users[i]
//                Body1(text = user.nickname)
//            }
//        }
//    }
//}


//mainViewModel: MainViewModel,
//viewModel: UserViewModel = hiltViewModel()
//){
//
////    val swipeRefreshState = rememberSwipeRefreshState(
////        isRefreshing = viewModel.state.isRefreshing
////    )
//
//    val state = viewModel.state
//
//    var i = 0
//    while (i < 100){
//        viewModel.insertUser(User(nickname = "@Pashka", id = 1))
//        ++i
//    }
//
//    Log.e("USERS",state.toString())
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(state.users!!.size) { i ->
//                val user = state.users[i]
//                Body1(text = user.nickname)
//            }
//        }
//    }
//}