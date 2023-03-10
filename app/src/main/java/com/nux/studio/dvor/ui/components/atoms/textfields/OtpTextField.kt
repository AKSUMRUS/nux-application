package com.nux.studio.dvor.ui.components.atoms.textfields

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH4

@Composable
fun OtpTextField(
    otpText: String,
    otpCount: Int = 4,
    modifier: Modifier = Modifier,
    onOtpTextChange: (String, Boolean) -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharBox(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}


@Composable
private fun CharBox(
    index: Int,
    text: String
) {
    val isFocused = index == text.length
    val char = when {
        index > text.length -> ""
        index == text.length -> "0"
        else -> text[index].toString()
    }

    HeadlineH4(
        modifier = Modifier
            .width(40.dp)
            .padding(2.dp)
            .border(
                width = 1.dp,
                color = when {
                    isFocused -> MaterialTheme.colors.onBackground
                    else -> MaterialTheme.colors.onPrimary
                },
                shape = RoundedCornerShape(8.dp)
            ),
        text = char,
        color = if (isFocused) {
            MaterialTheme.colors.onPrimary
        } else {
            MaterialTheme.colors.onBackground
        },
        textAlign = TextAlign.Center
    )
}