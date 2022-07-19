package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.Password
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.PhoneNumber
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val (nickname,setNickname) = remember{ mutableStateOf("AKSUM") }
    val (password,setPassword) = remember{ mutableStateOf("aksum2019") }

    val buttonClick = {
        viewModel.onEvent(
            ProfileEvent.Login(
                nickname = nickname,
                password = password
            )
        )
//        navController.navigate("verify_phone"){
//            popUpTo("verify_phone")
//            launchSingleTop = true
//        }
    }

    fun buttonBackClick(){
        navController.popBackStack()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar(buttonBackClick = {buttonBackClick()})

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TitleRegistration(
                title = stringResource(R.string.login),
                description = stringResource(R.string.description_login),
            )


            PhoneNumber()
//            TextFieldTrailingImage(
//                text = nickname,
//                placeholder = stringResource(id = R.string.nickname),
//                onValueChange = setNickname,
//                buttonClick = {
//                    setNickname("")
//                },
//                image = ImageBitmap.imageResource(id = R.drawable.cross),
//            )
//
//            Password(
//                password = password,
//                placeholder = stringResource(id = R.string.password),
//                onValueChange = setPassword,
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                ButtonWithIcon(
                    icon = Icons.Default.ArrowForward,
                    onClick = buttonClick,
                    modifier = Modifier,
                )
            }
        }
    }

}