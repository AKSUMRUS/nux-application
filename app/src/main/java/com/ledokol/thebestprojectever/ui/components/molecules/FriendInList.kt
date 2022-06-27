package com.ledokol.thebestprojectever.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.ledokol.thebestprojectever.ui.components.atoms.Body1

//import com.ledokol.thebestprojectever.ui.theme.Background

@Composable
fun FriendInList(name: String, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round)))
            .clickable(onClick = onClick )
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
        Body1(text = name,
        modifier = Modifier.padding(start = 10.dp))
    }
}