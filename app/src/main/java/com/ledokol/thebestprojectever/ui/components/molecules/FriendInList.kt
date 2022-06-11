package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.theme.TextColorNormal
import com.ledokol.thebestprojectever.ui.theme.blockBackground

//import com.ledokol.thebestprojectever.ui.theme.Background

@Composable
fun FriendInList(name: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round)))
            .background(blockBackground)
            .padding(10.dp)
    ){
        Icon(
            bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
            contentDescription = "Аноним",
            modifier = Modifier
                .heightIn(max = 50.dp)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
            tint = Color.Unspecified,
        )
        Text(text = name,
            color = TextColorNormal,
        modifier = Modifier.padding(start = 10.dp))
    }
}