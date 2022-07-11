package com.ledokol.thebestprojectever.ui.components.atoms.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
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
){
    Button(
        onClick = onClick,
        modifier = Modifier
//            .then(modifier)
//            .height(30.dp)
        ,
        shape = RoundedCornerShape(0.dp),
    ){
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier
                .padding(0.dp)
//                .fillMaxSize()
            ,
//            contentScale = FillBounds,
        )
    }
}