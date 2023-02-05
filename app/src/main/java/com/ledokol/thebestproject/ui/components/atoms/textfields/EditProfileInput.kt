package com.ledokol.thebestproject.ui.components.atoms.textfields

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
import com.ledokol.thebestproject.ui.components.atoms.texts.Body2
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditProfileInput(
    mainText: String,
    text: String,
    exists: Boolean? = null,
    modifier: Modifier = Modifier,
    initText: String? = null,
    description: String? = null,
    checkCorrect: (String) -> Boolean,
    onValueChange: (String) -> Unit,
    ) {
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
    ) {
        HeadlineH5(
            text = mainText,
            fontWeight = FontWeight.W700,
        )
        if (description != null) {
            Body2(
                text = description,
                modifier = Modifier.padding(bottom = 10.dp),
                color = MaterialTheme.colors.secondaryVariant,
            )
        }

        TextField(
            text = text,
            onValueChange = onValueChange,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = {
                keyboard!!.hide()
            }),
            modifier = Modifier.padding(top = 5.dp)
        )

        if (!checkCorrect(text)) {
            Body2(
                text = "Некорректный ввод",
                color = MaterialTheme.colors.error,
            )
        } else {
            if (exists != null) {
                if (!exists || initText == text) {
                    Body2(
                        text = "Свободен",
                        color = Color.Green,
                    )
                } else {
                    Body2(
                        text = "Занято",
                        color = MaterialTheme.colors.error,
                    )
                }
            }
        }
    }
}