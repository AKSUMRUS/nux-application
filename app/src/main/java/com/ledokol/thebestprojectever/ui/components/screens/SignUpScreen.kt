package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Button
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH1
import com.ledokol.thebestprojectever.ui.components.atoms.TextButton
import com.ledokol.thebestprojectever.ui.components.atoms.TextField


@Composable
fun SignUpScreen(
    viewModel: MainViewModel,
    navController: NavController
){
    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (password,setPassword) = remember{ mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadlineH1(text = stringResource(R.string.sign_up))
        TextField(
            placeholder = stringResource(R.string.nickname),
            text = nickname,
            onValueChange = { setNickname(it) },
        )
        TextField(
            placeholder = stringResource(R.string.password),
            text = password,
            onValueChange = { setPassword(it) },
        )
        Button(text = stringResource(R.string.sign_up), onClick = {
            viewModel.signUp(nickname = nickname,password = password)
        })
        TextButton(text = stringResource(R.string.dont_have_an_account), onClick = {
            navController.navigate("login_screen") {
                popUpTo("login_screen")
                launchSingleTop = true
            }
        })
    }
}