package com.ledokol.thebestprojectever.ui.components.atoms.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.*
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
        HeadlineH5(
            text = mainText,
            fontWeight = FontWeight.W700,
        )

        if(description != null) {
            Body2(
                text = description,
                modifier = Modifier.padding(bottom = 10.dp),
                color = MaterialTheme.colors.onSecondary,
            )
        }

        TextField(
            text = text,
            onValueChange = onValueChange,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions ( onDone = {
                keyboard!!.hide()
            } )
        )
    }

}