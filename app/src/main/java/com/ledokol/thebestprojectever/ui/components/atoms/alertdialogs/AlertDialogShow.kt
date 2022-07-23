package com.ledokol.thebestprojectever.ui.components.atoms.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@Composable
fun AlertDialogShow(
    openDialog: Boolean,
    label: String,
    description: String,
    buttonTextYes: String,
    buttonTextNo: String,
    onClose: () -> Unit,
    onAction: () -> Unit,
) {
    if(openDialog){
        AlertDialog(
            onDismissRequest = {
                onClose()
            },
            title = { HeadlineH5(text = label) },
            text = { Body1(text = description) },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.weight(1f).padding(10.dp),
                        onClick = { onClose() }
                    ) {
                        Body1(text = buttonTextNo)
                    }
                    Button(
                        modifier = Modifier.weight(1f).padding(10.dp),
                        onClick = { onAction() }
                    ) {
                        Body1(text = buttonTextYes)
                    }
                }
            }
        )
    }
}