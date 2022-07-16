package com.ledokol.thebestprojectever.ui.components.atoms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BasicTextField(
//    label: String,
    text: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = true,
    ) {
//    androidx.compose.foundation.text.BasicTextField(
//        value = text,
//        onValueChange = { onValueChange(it) },
//        placeholder = { Text(text = placeholder) },
//        keyboardOptions = KeyboardOptions(
//            keyboardType = keyboardType
//        ).copy(imeAction = imeAction),
//        keyboardActions = keyboardActions,
//        modifier = modifier.fillMaxWidth(),
//        singleLine = true,
//        colors = TextFieldDefaults.textFieldColors(
//            textColor = MaterialTheme.colors.onPrimary,
//            placeholderColor = MaterialTheme.colors.onSecondary,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            backgroundColor = Color.Transparent,
//            cursorColor = Color.DarkGray
//        ),
//        enabled = enabled,
//    )
}

