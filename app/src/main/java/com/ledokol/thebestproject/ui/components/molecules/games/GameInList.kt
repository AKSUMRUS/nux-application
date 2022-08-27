package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1

@Composable
fun GameInList(
    packageName: String,
    name: String,
    icon: String,
    iconLarge: String,
    backgroundImage: ImageBitmap,
    onClick: () -> Unit = {},
    openGame: Boolean = false,
    usageTime: String? = null,
){

    Column (
        modifier = Modifier
            .clickable {
                onClick()
            }
        ,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ){
            AsyncImage(
                model = iconLarge,
                contentDescription = "GameImage",
                modifier = Modifier
//                    .fillMaxWidth()
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

        }

        usageTime?.let {
            Body1(
                text = "Играл в эту игру $it минут",
                modifier = Modifier
                    .padding(top = 0.dp, start = 20.dp)
//                    modifier = Modifier.align(Alignment.BottomCenter)
            )
        }


    }
}