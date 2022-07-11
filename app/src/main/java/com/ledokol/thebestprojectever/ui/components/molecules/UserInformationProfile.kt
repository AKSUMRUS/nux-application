package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.*

@Composable
fun UserInformationProfile(
    name: String,
    profile: Boolean,
){

    val top: Dp = if (!profile) 70.dp else 120.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = top, bottom = 10.dp)
    ){
        Column(
            modifier = Modifier
                .weight(2f),
        ){
            if(profile){
                Body1(
                    text = stringResource(id = R.string.good_evening),
                    color = MaterialTheme.colors.onBackground,
                )
            }
            HeadlineH4(
                text = name,
                fontWeight = FontWeight.W700,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .weight(1f)
            ,
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
        }
    }
}