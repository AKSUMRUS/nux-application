package com.ledokol.thebestprojectever.ui.components.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.TextFieldWithCaption
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull

@Composable
fun VerifyPhone(){
    
    var code by remember{ mutableStateOf("")}

    fun onCodeChange(text:String){
        code = text
    }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        HeadlineH4(text = stringResource(id = R.string.verify_code))

        TextFieldWithCaption(label="Код", text = code, onValueChange = {onCodeChange(it)})
        
        ButtonPrimaryFull(text = stringResource(id = R.string.confirm)) {
            
        }
    }


}