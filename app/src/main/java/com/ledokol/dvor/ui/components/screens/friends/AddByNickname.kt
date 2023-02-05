package com.ledokol.dvor.ui.components.screens.friends

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.dvor.R
import com.ledokol.dvor.data.local.user.UserEvent
import com.ledokol.dvor.presentation.ProfileViewModel
import com.ledokol.dvor.presentation.UserViewModel
import com.ledokol.dvor.ui.components.atoms.Cross
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonFull
import com.ledokol.dvor.ui.components.atoms.textfields.SearchBorder
import com.ledokol.dvor.ui.components.atoms.texts.HeadlineH4
import com.ledokol.dvor.ui.navigation.ScreenRoutes


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddByNickname(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel,
) {
    var nickname by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    LaunchedEffect(true) {
        userViewModel.onEvent(UserEvent.ClearFriendUser)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
    ) {

        HeadlineH4(
            text = stringResource(id = R.string.add_by_nickname_title_screen),
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.TopStart),
            fontWeight = Black,
            color = MaterialTheme.colors.onBackground

        )

        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp, bottom = 100.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBorder(
                placeholder = stringResource(id = R.string.nickname),
                text = nickname,
                onValueChange = {
                    nickname = it
                },
                modifier = Modifier
                    .focusRequester(focusRequester),
            )

            ButtonFull(
                text = stringResource(id = R.string.search_by_nickname),
                onClick = {
                    userViewModel.onEvent(UserEvent.GetUserByNickname(nickname = nickname))
                    navController.navigate(ScreenRoutes.PREVIEW_FRIEND) {
                        popUpTo(ScreenRoutes.PREVIEW_FRIEND)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                padding = PaddingValues(10.dp),
            )
        }

        Cross(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}