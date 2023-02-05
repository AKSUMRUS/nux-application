package com.ledokol.thebestproject.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.ui.components.atoms.textfields.Search

@Preview
@Composable
private fun SearchPreview() {
    var text by remember { mutableStateOf("") }
    Search(
        text = text,
        onValueChange = { text = it }
    )
}

@Composable
fun Search(
    text: String,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(id = R.string.enter_nickname_search),
    onValueChange: (String) -> Unit,
    onTrailingButtonClick: (() -> Unit)? = null
) {
    val onTrailingClick = onTrailingButtonClick ?: { onValueChange("") }
    Box(modifier = modifier) {
        Search(
            placeholder = placeholder,
            text = text,
            icon = Icons.Default.Close,
            onValueChange = onValueChange,
            trailingButtonClick = onTrailingClick,
        )
    }
}