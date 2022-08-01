package com.ledokol.thebestprojectever.ui.components.atoms.textfields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.UserViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowSearch(
    userViewModel: UserViewModel
){
    var textSearch by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    Search(
        placeholder = stringResource(id = R.string.enter_nickname_search),
        text = textSearch,
        icon = Icons.Default.Close,
        onValueChange = {
            textSearch = it
            userViewModel.onEvent(UserEvent.OnSearchQueryChange(textSearch))
        },
        trailingButtonClick = {
            textSearch = ""
            userViewModel.onEvent(UserEvent.OnSearchQueryChange(textSearch))
        },
        modifier = Modifier,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions ( onDone = {
            keyboard!!.hide()
        } )
    )
}
