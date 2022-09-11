package com.ledokol.thebestproject.ui.components.atoms.alertdialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5

@Composable
fun AlertDialogShow(
    openDialog: Boolean,
    label: String,
    description: String,
    buttonTextYes: String,
    buttonTextNo: String,
    onActionSecondary: () -> Unit,
    onActionPrimary: () -> Unit,
    onClose: () -> Unit = onActionSecondary,
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
                        onClick = { onActionSecondary() }
                    ) {
                        Body1(text = buttonTextNo)
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
//                            .background(MaterialTheme.colors.secondary)
                        ,
                        onClick = { onActionPrimary() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                    ) {
                        Body1(text = buttonTextYes)
                    }
                }
            }
        )
    }
}