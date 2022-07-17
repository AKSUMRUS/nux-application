package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.TextFieldWithCaption
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import kotlinx.coroutines.delay

@Composable
fun VerifyPhone(
    profileViewModel: ProfileViewModel,
){
    
    var code by remember { mutableStateOf("")}

    fun onCodeChange(text:String){
        code = text
    }

    LaunchedEffect(key1 = true, block = {
        delay(3000)

        profileViewModel.login("goracio","1")
    })
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        HeadlineH4(text = stringResource(id = R.string.verify_code))

        TextFieldWithCaption(
            label="Код",
            text = code,
            onValueChange = {onCodeChange(it)},
            modifier = Modifier.padding(10.dp)
        )
        
        ButtonPrimaryFull(
            text = stringResource(id = R.string.confirm),
            onClick = {},
            modifier = Modifier.padding(10.dp)
        )
    }


}