package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.*

@Composable
fun UserInformationProfile(name: String, cntGames: Int, cntFriends: Int){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 120.dp, bottom = 10.dp)
            .padding(10.dp)
    ){
        Body1(
            text = "Добрый вечер,",
            type = "background"
        )
        HeadlineH4(
            text = name,
            fontWeight = FontWeight.W700,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        ){
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 120.dp, width = 120.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round)))
                    .align(CenterVertically)
                ,
                tint = Color.Unspecified,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f),
            ){
                Column(){
                    HeadlineH5(
                        text = cntGames.toString(),
                        type = "background",
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.align(CenterHorizontally),
                    )
                    Body1(
                        text = "Игр",
                        type = "background",
                        modifier = Modifier.align(CenterHorizontally),
                    )
                }
                Column(){
                    HeadlineH5(
                        text = cntFriends.toString(),
                        type = "background",
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.align(CenterHorizontally),
                    )
                    Body1(
                        text = "Друзей",
                        type = "background",
                        modifier = Modifier.align(CenterHorizontally),
                    )
                }
            }
        }
    }
}