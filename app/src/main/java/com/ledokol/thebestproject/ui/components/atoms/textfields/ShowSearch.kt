package com.ledokol.thebestproject.ui.components.atoms.textfields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.presentation.UserViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowSearch(
    userViewModel: UserViewModel,
    textSearch: String,
    onValueChange: (String) -> Unit,
){
    val keyboard = LocalSoftwareKeyboardController.current

    Search(
        placeholder = stringResource(id = R.string.enter_nickname_search),
        text = textSearch,
        icon = Icons.Default.Close,
        onValueChange = {
            onValueChange(it)
        },
        trailingButtonClick = {
            if(textSearch!=""){
                onValueChange("")
            }
        },
        modifier = Modifier,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions ( onDone = {
            keyboard!!.hide()
        } )
    )
}
