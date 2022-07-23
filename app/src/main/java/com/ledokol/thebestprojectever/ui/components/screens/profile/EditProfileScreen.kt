package com.ledokol.thebestprojectever.ui.components.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.TextButton
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithChangeableColor
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.EditProfileInput
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.profile.EditProfileTopBlock
import org.checkerframework.common.subtyping.qual.Bottom

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val state = profileViewModel.state.profile

    var name by remember{ mutableStateOf(state!!.name) }
//    var phone by remember{ mutableStateOf(state!!.phone) }
    var isSaved by remember { mutableStateOf(true) }

    fun onEdited(){
        isSaved = false
    }

    fun onEditName(newName: String){
        name = newName
        onEdited()
    }

    fun onSave(){
        if(!isSaved) {
            isSaved = true
            profileViewModel.onEvent(ProfileEvent.UpdateProfileData(newProfile = state!!))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackToolbar (
            buttonBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .padding(top = 120.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            ,
        ) {
            EditProfileTopBlock(
                profileViewModel = profileViewModel,
            )
            EditProfileInput(
                mainText = stringResource(id = R.string.name),
                description = stringResource(id = R.string.for_notifications),
                text = name,
                onValueChange = { onEditName(it) },
            )
        }

        ButtonWithChangeableColor(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
            ,
            text1 = stringResource (id = R.string.save_changes),
            text2 = stringResource(id = R.string.changes_saved),
            color1 = MaterialTheme.colors.primary,
            color1Text = MaterialTheme.colors.onPrimary,
            color2 = MaterialTheme.colors.surface,
            color2Text = MaterialTheme.colors.onSurface,
            isClicked = isSaved,
            onClick = { onSave() }
        )
    }

}

@Preview
@Composable
fun EditProfileScreen_preview(){
//    EditProfileScreen()
}