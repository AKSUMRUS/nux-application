package com.ledokol.thebestproject.ui.components.atoms.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Delete,
    modifier: Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
//            .then(modifier)
//            .height(30.dp)
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier
                .padding(2.dp)
//                .fillMaxSize()
            ,
            tint = MaterialTheme.colors.onBackground
//            contentScale = FillBounds,
        )
    }
}