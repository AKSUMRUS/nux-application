package com.ledokol.thebestprojectever.ui .components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary

@Composable
fun ProfileTopBlock(
    profileViewModel: ProfileViewModel,
){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        if(profileViewModel.state.profile != null){
            profileViewModel.state.profile?.let {
                UserInformationProfile(
                    name = it.nickname,
                    profile = true,
                )
            }
        }

        ButtonPrimary(
            text = stringResource(id = R.string.logout),
            onClick = {
                profileViewModel.clearProfile()
            },
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

    }

}