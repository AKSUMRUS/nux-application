package com.ledokol.thebestprojectever.ui.components.atoms.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.ledokol.thebestprojectever.ui.components.atoms.Body2
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileInput(
    mainText: String,
    description: String?,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        HeadlineH4(text = mainText)

        if(description != null) {
            Body2(text = description)
        }

        TextField(
            text = text,
            onValueChange = onValueChange,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions ( onNext = {
                keyboard!!.hide()
            } )
        )
    }

}