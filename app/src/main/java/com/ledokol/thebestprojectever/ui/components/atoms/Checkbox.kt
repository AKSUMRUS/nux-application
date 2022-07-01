package com.ledokol.thebestprojectever.ui.components.atoms

import android.widget.CheckBox
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Checkbox(
    value: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier,
){
    androidx.compose.material.Checkbox(
        checked = value,
        onCheckedChange = onChange,
        modifier = Modifier
            .then(modifier),
    )
}