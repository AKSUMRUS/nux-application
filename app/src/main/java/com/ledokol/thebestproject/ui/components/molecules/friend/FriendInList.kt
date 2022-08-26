package com.ledokol.thebestproject.ui.components.molecules.friend

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ledokol.thebestproject.data.local.user.User
import com.ledokol.thebestproject.ui.components.atoms.Status
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1

@Composable
fun FriendInList(
    user: User,
    onClick: () -> Unit,
    clicked: Boolean = false,
){

    val modifier: Modifier = if (clicked) Modifier
        .padding(bottom = 5.dp)
        .border(3.dp, MaterialTheme.colors.primary)
        .fillMaxWidth()
    else Modifier
        .padding(bottom = 5.dp)
        .fillMaxWidth()

    Box(
        modifier = modifier
        ,
    ){

        if(user.status.in_app && user.status.app!=null){
            user.status.app?.let {
                AsyncImage(
                    it.image_wide,
                    contentDescription = "user back",
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    alpha = 0.3f,
                    contentScale = Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(0))
                .clickable(onClick = onClick)
                .padding(10.dp)
        ){
            AsyncImage(
                model = user.profile_pic,
                contentDescription = "Аноним",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                ,
                contentScale = Crop,
            )
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