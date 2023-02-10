package com.ledokol.thebestproject.ui.components.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ledokol.thebestproject.ui.components.atoms.textfields.Search
import com.nux.studio.dvor.core_ui.R

@Composable
fun Search(
    text: String,
    placeholder: String = stringResource(id = R.string.enter_nickname_search),
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Search(
            placeholder = placeholder,
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