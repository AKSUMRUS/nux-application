package com.ledokol.thebestprojectever.ui.components.screens.registration

import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.screens.SignUpScreenFirst
import com.ledokol.thebestprojectever.ui.components.screens.SignUpScreenSecond

@Composable
fun SignUpScreen(
    viewModel: MainViewModel,
    navController: NavController
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var secondScreen by remember { mutableStateOf(false) }
    var checkPrivacy by remember{ mutableStateOf(false) }

    val buttonNextClickSecond = {
        Log.e("REGISTER","REGISTERED")
        viewModel.signUp(nickname, password,email,name)
    }

    val buttonNextClickFirst = {
        secondScreen = true
    }

    if(!secondScreen){
        fun buttonBackClick(){
            navController.popBackStack()
        }
        SignUpScreenFirst(
            navController = navController,
            email = email,
            setEmail = {
                email = it
            },
            password = password,
            setPassword = {
                password = it
            },
            buttonBackClick = { buttonBackClick() },
            buttonNextClick = buttonNextClickFirst,
        )
    }else{
        fun buttonBackClick(){
            secondScreen = false
        }

        SignUpScreenSecond(
            nickname = nickname,
            setNickname = {
                nickname = it
            },
            name = name,
            setName = {
                name = it
            },
            buttonNextClick = buttonNextClickSecond,
            buttonBackClick = {buttonBackClick() },
            checkPrivacy = checkPrivacy,
            setCheckPrivacy = {
                checkPrivacy = it
            },
        )
    }


}