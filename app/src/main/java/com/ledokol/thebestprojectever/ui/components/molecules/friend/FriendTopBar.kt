package com.ledokol.thebestprojectever.ui.components.molecules.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.Status
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4

@Composable
fun FriendTopBar(
    user: User
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 80.dp, bottom = 10.dp)
        ){
            Column(
                modifier = Modifier
//                    .align(Alignment.CenterVertically)
                    .weight(2f),
            ){
                HeadlineH4(
                    text = user.nickname,
                    fontWeight = FontWeight.W700,
                )

                FriendStatus(user = user)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                    .weight(1f)
                ,
                verticalAlignment = Alignment.CenterVertically,
            ){

                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                    contentDescription = "Аноним",
                    modifier = Modifier
                        .size(height = 120.dp, width = 120.dp)
                    ,
                    contentScale = ContentScale.Crop,
                )
            }
        }
}


@Preview
@Composable
fun FriendTopBar_preview(
    
) {
    FriendTopBar(user = User(nickname = "test", status = Status(finished = false),id = ""))
}