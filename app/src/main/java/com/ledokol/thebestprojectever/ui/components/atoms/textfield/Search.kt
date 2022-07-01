package com.ledokol.thebestprojectever.ui.components.atoms.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5

@Composable
fun Search(
    placeholder: String,
    text: String,
    icon: ImageVector,
    textCaption: String? = null,
    onValueChange: (String) -> Unit,
    trailingButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
) {
    if(textCaption != null){
        HeadlineH5(
            text = textCaption,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(top=10.dp)
        )
    }

    androidx.compose.material.TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(fontSize = 17.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ).copy(imeAction = imeAction),
        keyboardActions = keyboardActions,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick =  trailingButtonClick) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colors.onSecondary)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 15.dp)
            .background(
                MaterialTheme.colors.secondary,
                RoundedCornerShape(0.dp)
            )
            .then(modifier),
        placeholder = { Text(text = placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            placeholderColor = MaterialTheme.colors.onSecondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            cursorColor = Color.DarkGray
        )
    )
}

