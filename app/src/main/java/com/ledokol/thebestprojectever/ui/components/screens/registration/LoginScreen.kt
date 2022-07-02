package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (password,setPassword) = remember{ mutableStateOf("") }

    val buttonClick = {
        viewModel.login(nickname = nickname,password = password)
        navController.navigate("quick_game") {
            popUpTo("quick_game")
            launchSingleTop = true
        }
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

            TextFieldTrailingIcon(
                text = nickname,
                placeholder = stringResource(id = R.string.email),
                onValueChange = setNickname,
                buttonClick = {
                    setNickname("")
                },
                icon = Icons.Default.Close,
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
                    onClick = buttonClick,
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