package com.ledokol.thebestprojectever.ui.components.atoms.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ledokol.thebestprojectever.ui.components.atoms.Body2
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@Composable
fun EditProfileInput(
    mainText: String,
    description: String?,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

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
        )
    }

}