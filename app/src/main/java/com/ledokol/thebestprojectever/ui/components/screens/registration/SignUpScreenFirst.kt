package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonWithIcon
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreenFirst(
    viewModel: MainViewModel,
    navController: NavController
){
//    val retrofitServices: RetrofitServices = Common.retrofitService
    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (name,setName) = remember{ mutableStateOf("") }

    val buttonClick = {
        navController.navigate("signup_screen_second") {
            popUpTo("signup_screen_second")
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackToolbar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {

            Column(
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                HeadlineH2(
                    text = stringResource(R.string.sign_up),
                    fontWeight = FontWeight.W700,
                )
                HeadlineH6(
                    text = stringResource(R.string.description_signup_first),
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onBackground,
                )
            }
            TextFieldTrailingIcon(
                textCaption = stringResource(R.string.choose_nickname),
                text = nickname,
                placeholder = stringResource(id = R.string.profile_nickname),
                onValueChange = setNickname,
                buttonClick = {
                    setNickname("")
                },
                icon = Icons.Default.Close,
            )

            TextFieldTrailingIcon(
                textCaption = stringResource(R.string.choose_name),
                text = name,
                placeholder = stringResource(id = R.string.profile_name),
                onValueChange = setName,
                buttonClick = {
                    setName("")
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
                    modifier = Modifier.background(MaterialTheme.colors.primary),
                )
            }
            TextButton(text = stringResource(R.string.dont_have_an_account), onClick = {
                navController.navigate("login_screen") {
                    popUpTo("login_screen")
                    launchSingleTop = true
                }
            })
        }
    }
}