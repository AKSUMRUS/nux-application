package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull

@Composable
fun Contact(
    name: String,
    navController: NavController,
){
    Column(
        modifier = Modifier
            .padding(20.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(10.dp)
    ){
        HeadlineH5(text = name)
        ButtonPrimaryFull(
            text = stringResource(id = R.string.invite_contact),
            onClick = {
                navController.navigate("share_screen"){
                    popUpTo("share_screen")
                    launchSingleTop = true
                }
            }
        )
    }
}