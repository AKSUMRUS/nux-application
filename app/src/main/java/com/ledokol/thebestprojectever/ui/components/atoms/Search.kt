package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.textfields.Search

@Composable
fun Search(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier,
    ){
        Search(
            placeholder = stringResource(id = R.string.enter_nickname_search),
            text = text,
            icon = Icons.Default.Close,
            onValueChange = {
                onValueChange(it)
            },
            trailingButtonClick = {
                onValueChange("")
            },
        )
    }

}