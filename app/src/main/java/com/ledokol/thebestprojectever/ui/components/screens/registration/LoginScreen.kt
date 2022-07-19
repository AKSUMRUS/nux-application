package com.ledokol.thebestprojectever.ui.components.screens.registration

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.PhoneNumberOld
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.TitleRegistration


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val context: Context = LocalContext.current

    val (nickname,setNickname) = remember{ mutableStateOf("goracio") }
    val (password,setPassword) = remember{ mutableStateOf("1") }

    var phone by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf("")
    }

    fun onPhoneChange(text: String){
        var str = text
        str = str.replace("(","")
        str = str.replace(")","")
        str = str.replace("-","")

        if(str.length>10){
            return
        }
        phone = str
    }


    val buttonClick = {
        if(phone.length!=10){
            error = context.resources.getString(R.string.wrong_phone_number)
        }else{
            navController.navigate("verify_phone"){
                popUpTo("verify_phone")
                launchSingleTop = true
            }
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


            PhoneNumber(
                phone = phone,
                onPhoneChange = {onPhoneChange(it)},
                error = error,
                onNextClick = {buttonClick()}
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
        }
    }

}