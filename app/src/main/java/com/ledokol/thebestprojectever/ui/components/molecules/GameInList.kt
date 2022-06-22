package com.ledokol.thebestprojectever.ui.components.molecules

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6

@Composable
fun GameInList(name: String, icon: ImageBitmap, backgroundImage: ImageBitmap){

    Row(
        modifier = Modifier
            .padding(20.dp)
    ){
        Icon(
            bitmap = icon,
            contentDescription = "fdfd",
            modifier = Modifier.align(CenterVertically),
            tint = Color.Unspecified,
        )

        Box(
            modifier = Modifier
                .padding(start = 10.dp)
        ){
            Image(
                bitmap = backgroundImage,
                contentDescription = "fdfd",
                modifier = Modifier.fillMaxSize(),
            )

            HeadlineH6(
                text = name,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}