package com.nux.studio.dvor.ui.components.molecules.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.navigation.ScreenRoutes

@Composable
fun ProfileTopBlock(
    profileViewModel: ProfileViewModel,
    navController: NavController,
    userViewModel: UserViewModel,
) {

    val state = profileViewModel.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        if (state.profile != null) {
            UserInformationProfile(
                name = state.profile!!.nickname,
                profile_pic = state.profile!!.profile_pic!!,
                profile = true,
                onClickEdit = {
                    navController.navigate(ScreenRoutes.EDIT_PROFILE) {
                        popUpTo(ScreenRoutes.EDIT_PROFILE)
                        launchSingleTop = true
                    }
                },
                profileViewModel = profileViewModel,
                userViewModel = userViewModel,
            )
        }
    }

}