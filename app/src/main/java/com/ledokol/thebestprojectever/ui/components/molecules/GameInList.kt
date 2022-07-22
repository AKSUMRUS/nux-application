package com.ledokol.thebestprojectever.ui.components.molecules

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun GameInList(
    packageName: String,
    name: String,
    icon: String = "https://storage.yandexcloud.net/nux/pubg.png",
    iconLarge: String = "https://storage.yandexcloud.net/nux/pubg.png",
    backgroundImage: ImageBitmap,
    onClick: () -> Unit = {},
){

    Log.e("iconLink", icon)

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable {
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

//        Body1(
//            text = name,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
    }

}