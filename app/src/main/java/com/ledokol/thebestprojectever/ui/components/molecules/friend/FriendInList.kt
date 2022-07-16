package com.ledokol.thebestprojectever.ui.components.molecules.friend

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.ui.components.atoms.Status
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

//import com.ledokol.thebestprojectever.ui.theme.Background

@Composable
fun FriendInList(
    user: User,
    onClick: () -> Unit,
    clicked: Boolean = false,
){

    val modifier: Modifier = if (clicked) Modifier
        .padding(bottom = 20.dp)
        .height(80.dp)
        .fillMaxWidth()
        .border(3.dp, MaterialTheme.colors.primary)
    else Modifier
        .padding(bottom = 20.dp)
        .height(80.dp)
        .fillMaxWidth()

    Box(
        modifier = modifier
        ,
    ){

        if(!user.status.finished){
            AsyncImage(
                user.status.current_app!!.image_wide,
                contentDescription = "user back",
                modifier = Modifier.fillMaxWidth(),
                contentScale = FillWidth
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .clickable(onClick = onClick)
                .padding(10.dp)
        ){
            Box(
            ){
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                    contentDescription = "Аноним",
                    modifier = Modifier
                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round)))
                    ,
                    tint = Color.Unspecified,
                )
            }
            Body1(text = user.nickname,
                modifier = Modifier.padding(start = 10.dp))
        }

        Status(
            status = if(user.status.finished)"offline" else "online",
            modifier = Modifier
                .padding(top = 15.dp, end = 20.dp)
                .align(Alignment.TopEnd)
            ,
        )
    }
}

//@Preview
//@Composable
//fun FriendInList_preview(
//
//){
//    FriendInList(name = "aboba", onClick = { /*TODO*/ })
//}