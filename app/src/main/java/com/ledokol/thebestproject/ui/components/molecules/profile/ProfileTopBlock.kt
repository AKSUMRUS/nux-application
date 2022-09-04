package com.ledokol.thebestproject.ui.components.molecules.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel

@Composable
fun ProfileTopBlock(
    profileViewModel: ProfileViewModel,
    navController: NavController,
    userViewModel: UserViewModel,
){

    val state = profileViewModel.state

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        if(state.profile != null){
            UserInformationProfile(
                name = state.profile!!.nickname,
                profile_pic = state.profile!!.profile_pic!!,
                profile = true,
                onClickEdit = {
                    navController.navigate("edit_profile"){
                        popUpTo("edit_profile")
                        launchSingleTop = true
                    }
                },
                profileViewModel = profileViewModel,
                userViewModel = userViewModel,
            )
        }
    }

}