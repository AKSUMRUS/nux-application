package com.ledokol.thebestproject.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6
import com.ledokol.thebestproject.ui.components.molecules.friend.StripFriend

@Composable
fun GameStat(
    packageName: String,
    name: String,
    icon: String,
    iconLarge: String,
    backgroundImage: ImageBitmap,
    onClick: () -> Unit = {},
    openGame: Boolean = false,
    usageTime: String? = null,
){

    Box(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary)
        ,
    ){

        Row(
            modifier = Modifier
//                .height(80.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .clickable(onClick = onClick)
                .padding(10.dp)
        ){
            AsyncImage(
                model = icon,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                ,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp)
            ){
                HeadlineH6(
                    text = name,
                    modifier = Modifier,
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Medium,
                )

                Body1(text = "Играл ${usageTime} минуты",
                    modifier = Modifier
                        .padding(start = 0.dp)
                    ,
                    color = MaterialTheme.colors.onBackground,
                )
            }

        }
    }
}