package com.ledokol.thebestprojectever.ui.components.molecules.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.StatusViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4

@Composable
fun ProfileTopBlock(
    profileViewModel: ProfileViewModel,
    statusViewModel: StatusViewModel,
    navController: NavController,
){

    val state = profileViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        if(state.profile != null){
            UserInformationProfile(
                name = state.profile.nickname,
                profile_pic = state.profile.profile_pic!!,
                profile = true,
                onClickEdit = {
                    navController.navigate("edit_profile"){
                        popUpTo("edit_profile")
                        launchSingleTop = true
                    }
                },
                profileViewModel = profileViewModel,
            )
        }
        
        HeadlineH4(
            text = "Игры",
            modifier = Modifier.padding(start = 20.dp),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
        )
    }

}