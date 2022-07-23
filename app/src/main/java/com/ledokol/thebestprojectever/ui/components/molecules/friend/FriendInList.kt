package com.ledokol.thebestprojectever.ui.components.molecules.friend

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.ui.components.atoms.Status
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1

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

        if(user.status.online){
            Log.e("IMAGES","https://storage.yandexcloud.net/nux/icons/image_wide/"+user.status.current_app!!.android_package_name+".png")
            AsyncImage(
                "https://storage.yandexcloud.net/nux/icons/image_wide/"+user.status.current_app!!.android_package_name+".png",
                contentDescription = "user back",
                modifier = Modifier
                    .fillMaxWidth()
                ,
                alpha = 0.3f,
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
                modifier = Modifier.padding(start = 10.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }

        Status(
            status = if(user.status.online)"online" else "offline",
            modifier = Modifier
                .padding(top = 15.dp, end = 20.dp)
                .align(Alignment.TopEnd)
            ,
        )
    }
}