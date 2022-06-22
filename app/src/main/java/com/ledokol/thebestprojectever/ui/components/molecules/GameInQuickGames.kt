package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun GameInQuickGames(packageName: String, icon: ImageBitmap, backgroundImage: ImageBitmap){


//    Text(text = "fdklfdklfd")
    Row (
        modifier = Modifier.fillMaxWidth().size(100.dp),
    ) {
        Image(
            bitmap = icon,
            contentDescription = "GameImage",
            modifier = Modifier.size(width = 100.dp, height = 100.dp),
        )
//        Image(
//            bitmap = backgroundImage,
//            contentDescription = "GameImage",
//            modifier = Modifier.size(width = 100.dp, height = 100.dp),
//        )
    }

}