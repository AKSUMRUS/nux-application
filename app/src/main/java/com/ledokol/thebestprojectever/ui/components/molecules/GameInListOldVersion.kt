package com.ledokol.thebestprojectever.ui.components.molecules

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH6

@Composable
fun GameInListOldVersion(name: String, icon: ImageBitmap, backgroundImage: ImageBitmap){

    Box(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .height(80.dp)
    ){
        Image(
            bitmap = backgroundImage,
            contentDescription = "fdfd",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
//            tint = Color.Unspecified,
        )

        Row(
            modifier = Modifier
                .padding(start = 10.dp)
        ){
            Icon(
                bitmap = icon,
                contentDescription = "fdfd",
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
                tint = Color.Unspecified,
            )

            HeadlineH6(
                text = name,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}