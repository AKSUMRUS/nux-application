package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
//import com.ledokol.thebestprojectever.data.remote.Common
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.Password
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.TextFieldTrailingImage
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration


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

            TextFieldTrailingImage(
                text = email,
                placeholder = stringResource(id = R.string.email),
                buttonClick = {
                    setEmail("")
                },
                image = ImageBitmap.imageResource(id = R.drawable.cross),
                onValueChange = setEmail,
            )

            Password(
                password = password,
                placeholder = stringResource(id = R.string.password),
                onValueChange = setPassword,
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