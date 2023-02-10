package com.ledokol.dvor.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ledokol.dvor.ui.components.atoms.textfields.Search
import com.ledokol.dvor.R

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