package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.TextFieldWithCaption
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.navigation.BottomNavItemMain
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VerifyPhone(
    navController: NavController,
    profileViewModel: ProfileViewModel,
){
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current


    var code by remember { mutableStateOf("")}

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    fun onCodeChange(text:String){
        code = text
    }

    fun onClick(){
        profileViewModel.onEvent(
            ProfileEvent.Login(
                nickname = "AKSUM",
                password = "aksum2019"
            )
        )    }


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        BackToolbar(
            buttonBackClick = {
                navController.popBackStack()
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp,end = 50.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            HeadlineH4(
                text = stringResource(id = R.string.verify_code),
                fontWeight = FontWeight.W700,
            )
            Body1(
                text = stringResource(id = R.string.verify_code_description),
                color = MaterialTheme.colors.onSecondary,
            )

            TextField(
                text = code,
                onValueChange = {onCodeChange(it)},
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .focusRequester(focusRequester)
                ,
                keyboardType = KeyboardType.NumberPassword,
            )

            ButtonPrimaryFull(
                text = stringResource(id = R.string.confirm),
                onClick = {onClick()},
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
        }
    }


}