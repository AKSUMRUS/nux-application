package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonSecondary

@Composable
fun StartRegistrationScreen(
    navController: NavController,
    viewModel: MainViewModel
){

    fun onClickSignUp(): Unit{
        navController.navigate("signup_screen") {
            popUpTo("signup_screen")
            launchSingleTop = true
        }
    }

    fun onClickLogin(){
        navController.navigate("login_screen") {
            popUpTo("login_screen")
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeadlineH4(
            text = stringResource(id = R.string.hello_registration),
            modifier = Modifier.padding(bottom = 70.dp)
        )


        ButtonPrimary(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
//                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        ,
            onClick = {onClickSignUp()},
        )
        ButtonSecondary(
            text = stringResource(id = R.string.login),
            modifier = Modifier.fillMaxWidth(),
            onClick = {onClickLogin()},
        )
    }
}