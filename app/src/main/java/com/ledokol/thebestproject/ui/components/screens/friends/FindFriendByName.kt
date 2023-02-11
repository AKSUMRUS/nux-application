package com.ledokol.thebestproject.ui.components.screens.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.UserViewModel
import com.nux.studio.dvor.core_ui.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.textfields.TextField
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.molecules.BackToolbar

@Composable
fun FindFriendByName(
    userViewModel: UserViewModel,
    navController: NavController
) {
    val state = userViewModel.state.friendUser
    val error = userViewModel.state.error
    var name by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(
            buttonBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(top = 100.dp)
        ) {
            LaunchedEffect(state) {
                if (state != null) {
                    navController.navigate("add_friend_by_name") {
                        popUpTo("find_friend_by_name")
                        launchSingleTop = false
                    }
                }
            }

            TextField(text = name, onValueChange = { name = it })

            if (error != null) {
                Snackbar {
                    HeadlineH4(text = error)
                }
            }

            if (state == null) {
                ButtonFull(
                    text = "найти",
                    onClick = {
                        userViewModel.onEvent(UserEvent.ClearError)
                        userViewModel.onEvent(UserEvent.GetUserByNickname(name))
                    },
                )
            }
        }
    }
}