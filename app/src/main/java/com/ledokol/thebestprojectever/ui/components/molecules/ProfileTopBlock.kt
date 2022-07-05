package com.ledokol.thebestprojectever.ui.components.molecules

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Button

@Composable
fun ProfileTopBlock(
    mainViewModel: MainViewModel,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        UserInformationProfile(
            name = "Гордей",
            profile = true,
        )

        Button(
            text = stringResource(id = R.string.logout),
            onClick = {
                mainViewModel.clearProfile()
            }
        )

    }

}