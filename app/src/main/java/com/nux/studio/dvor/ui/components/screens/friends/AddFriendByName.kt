package com.nux.dvor.ui.components.screens.friends

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nux.studio.dvor.core_ui.atoms.buttons.ButtonFull
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH4
import com.nux.studio.dvor.core_ui.atoms.texts.HeadlineH5
import com.nux.studio.dvor.ui.navigation.BottomNavItemMain
import com.nux.studio.dvor.data.local.user.UserEvent
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.components.molecules.BackToolbar

@Composable
fun AddFriendByName(
    userViewModel: UserViewModel,
    navController: NavController
) {

    val state = userViewModel.state.friendUser
    var textButton by remember { mutableStateOf("Добавить в друзья") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(
            buttonBackClick = {
                userViewModel.state = userViewModel.state.copy(
                    friendUser = null
                )
                navController.popBackStack()
            }
        )
        Column(
            modifier = Modifier
                .padding(top = 100.dp)
        ) {

            AsyncImage(model = state?.profile_pic, contentDescription = "Profile picture")
            HeadlineH4(text = state?.name ?: "")
            HeadlineH5(text = state?.nickname ?: "")

            ButtonFull(
                text = textButton,
                onClick = {
                    textButton = "Отправлено"
                    Log.e("AddFriendByName", "Отправлено")
                    userViewModel.onEvent(UserEvent.AddFriendById)
                }
            )

            ButtonFull(
                text = "Отмена",
                onClick = {
                    userViewModel.state = userViewModel.state.copy(
                        friendUser = null
                    )
                    navController.navigate(BottomNavItemMain.Friends.screen_route) {
                        popUpTo(BottomNavItemMain.Friends.screen_route)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}