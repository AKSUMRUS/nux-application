package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@Composable
fun GameInList(
    packageName: String,
    name: String,
    icon: String,
    iconLarge: String,
    backgroundImage: ImageBitmap,
    onClick: () -> Unit = {},
){

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable() {
                onClick()
            }
        ,
    ) {
        AsyncImage(
            model = iconLarge,
            contentDescription = "GameImage",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
            ,
            contentScale = ContentScale.FillBounds,
        )
        AsyncImage(
            model = icon,
            contentDescription = "GameImage",
            modifier = Modifier
//                .border(5.dp, MaterialTheme.colors.background)
                .size(70.dp)
                .align(Alignment.BottomCenter)
            ,
        )

        Body1(
            text = name,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}