package com.ledokol.thebestprojectever.ui.components.atoms.textfields

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneNumber(
    phone: String,
    onPhoneChange: (String) -> Unit,
    error: String,
    onNextClick: () -> Unit,
){
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    var phone0 by remember{ mutableStateOf("+7")}

    fun onPhone0Change(text: String){
        phone0 = text
    }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        keyboard?.show()
    }

    var textStyle = MaterialTheme.typography.body1
    textStyle = textStyle.copy(
        textAlign = TextAlign.Center
    )

    Column(){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            TextField(
                text = phone0,
                onValueChange = { onPhoneChange(it) },
                modifier = Modifier
                    .padding(0.dp)
                    .weight(3f)
//                    .then(modifier)
                ,
                enabled = false,
                textStyle = textStyle,
            )
            TextField(
                text = phone,
                onValueChange = { onPhoneChange(it) },
                modifier = Modifier
                    .padding(0.dp)
                    .weight(10f)
                    .align(CenterVertically)
                    .focusRequester(focusRequester)
//                    .then(modifier)
                ,
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = {
                    onNextClick()
                }),
            )
        }

        Body1(
            text = error,
            color = MaterialTheme.colors.error,
        )
    }

}
