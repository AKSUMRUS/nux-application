package com.ledokol.thebestprojectever.ui .components.molecules

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Button

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
        if(profileViewModel.profile.value!= null){
            profileViewModel.profile.value?.let {
                UserInformationProfile(
                    name = it.nickname,
                    profile = true,
                )
            }
        }

        Button(
            text = stringResource(id = R.string.logout),
            onClick = {
                profileViewModel.clearProfile()
            },
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

    }

}