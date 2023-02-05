package com.ledokol.dvor.ui.components.atoms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyCheckbox(
    value: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier,
) {
    androidx.compose.material.Checkbox(
        checked = value,
        onCheckedChange = onChange,
        modifier = Modifier
            .then(modifier),
    )
}