package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.Profile
//import com.ledokol.thebestprojectever.data.remote.Common
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.ProfileJSON
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreenFirst(
    navController: NavController,
    email: String,
    setEmail: (String) -> Unit,
    password: String,
    setPassword: (String) -> Unit,
    buttonBackClick: () -> Unit,
    buttonNextClick: () -> Unit,
){

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(buttonBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TitleRegistration(
                title = stringResource(R.string.sign_up),
                description = stringResource(R.string.description_signup_first),
            )

            TextFieldTrailingIcon(
                text = email,
                placeholder = stringResource(id = R.string.email),
                buttonClick = {
                      setEmail("")
                },
                icon = Icons.Default.Close,
                onValueChange = setEmail,
            )

            TextFieldTrailingIcon(
                text = password,
                placeholder = stringResource(id = R.string.password),
                onValueChange = setPassword,
                buttonClick = {
                    setPassword("")
                },
                icon = Icons.Default.Close,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                ButtonWithIcon(
                    icon = Icons.Default.ArrowForward,
                    onClick = buttonNextClick,
                    modifier = Modifier,
                )
            }
//            TextButton(text = stringResource(R.string.dont_have_an_account), onClick = {
//                navController.navigate("login_screen") {
//                    popUpTo("login_screen")
//                    launchSingleTop = true
//                }
//            })
        }
    }
}