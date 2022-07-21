package com.ledokol.thebestprojectever.ui.components.molecules

import android.graphics.Bitmap
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
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

@Composable
fun GameActivity(
    packageName: String,
    icon: String = "https://storage.yandexcloud.net/nux/pubg.png",
    iconLarge: String = "https://storage.yandexcloud.net/nux/pubg.png",
    backgroundImage: ImageBitmap,
    startTime: String,
    finishTime: String,
    onClick: () -> Unit = {},
){

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable() {
                onClick()
            }
            .padding(top = 20.dp)
        ,
    ) {
        AsyncImage(
            model = iconLarge,
            contentDescription = "GameImage",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentScale = ContentScale.FillBounds,
        )
        AsyncImage(
            model = icon,
            contentDescription = "GameImage",
            modifier = Modifier
                .size(70.dp)
                .padding(15.dp)
                .align(Alignment.CenterStart),
        )
    }

}