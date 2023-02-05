package com.ledokol.dvor.ui.components.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.dvor.data.local.profile.ProfileEvent
import com.ledokol.dvor.data.local.user.UserEvent
import com.ledokol.dvor.domain.profile.UpdateProfile
import com.ledokol.dvor.domain.profile.UpdateProfileJSON
import com.ledokol.dvor.presentation.ProfileViewModel
import com.ledokol.dvor.presentation.UserViewModel
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.dvor.ui.components.atoms.buttons.ButtonWithChangeableColor
import com.ledokol.dvor.ui.components.atoms.textfields.EditProfileInput
import com.ledokol.dvor.ui.components.molecules.BackToolbar
import com.ledokol.dvor.ui.components.molecules.profile.EditProfileTopBlock
import com.ledokol.dvor.ui.components.screens.registration.checkCorrectName
import com.ledokol.dvor.ui.components.screens.registration.checkCorrectNickname
import com.ledokol.dvor.R
@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel,
) {
    val state = profileViewModel.state.profile
    val context = LocalContext.current

    var name by remember { mutableStateOf(state!!.name) }
    var nickname by remember { mutableStateOf(state!!.nickname) }
    var isSaved by remember { mutableStateOf(true) }

    fun onEdited() {
        isSaved = false
    }

    fun onEditName(newName: String) {
        name = newName
        onEdited()
    }

    fun onEditNickname(newNickname: String) {
        nickname = newNickname
        userViewModel.onEvent(UserEvent.CheckExistsNickname(nickname = nickname))
        onEdited()
    }

    fun onSave() {
        if (!checkCorrectName(name) || !checkCorrectNickname(nickname)) {
            Toast.makeText(context, "Данные некорректны", Toast.LENGTH_LONG).show()
        } else if ((userViewModel.state.existsUser == null || !userViewModel.state.existsUser!!) || nickname == state!!.nickname) {
            if (!isSaved) {
                isSaved = true
                profileViewModel.onEvent(
                    ProfileEvent.UpdateProfileData(
                        newProfile = UpdateProfileJSON(
                            user = UpdateProfile(
                                nickname = nickname,
                                name = name
                            )
                        )
                    )
                )
            }
        } else {
            Toast.makeText(context, "Такой никнейм уже существует", Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackToolbar(
            buttonBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            EditProfileTopBlock(
                profileViewModel = profileViewModel,
                userViewModel = userViewModel,
            )

            EditProfileInput(
                mainText = stringResource(id = R.string.name),
                text = name,
                onValueChange = { onEditName(it) },
                checkCorrect = {
                    checkCorrectName(it)
                }
            )

            EditProfileInput(
                mainText = stringResource(id = R.string.nickname),
                text = nickname,
                onValueChange = { onEditNickname(it) },
                modifier = Modifier
                    .padding(top = 10.dp),
                exists = userViewModel.state.existsUser,
                checkCorrect = {
                    checkCorrectNickname(it)
                }

            )

            ButtonWithChangeableColor(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                text1 = stringResource(id = R.string.save_changes),
                text2 = stringResource(id = R.string.changes_saved),
                color1 = MaterialTheme.colors.secondary,
                color1Text = MaterialTheme.colors.onSecondary,
                color2 = MaterialTheme.colors.surface,
                color2Text = MaterialTheme.colors.onSurface,
                isClicked = isSaved,
                onClick = { onSave() }
            )

            ButtonBorder(
                text = stringResource(id = R.string.logout),
                onClick = {
                    profileViewModel.onEvent(ProfileEvent.LogOut)
                },
                colorBorder = MaterialTheme.colors.error,
                padding = 2.dp,
                modifier = Modifier
                    .padding(top = 20.dp),
            )
        }

    }

}