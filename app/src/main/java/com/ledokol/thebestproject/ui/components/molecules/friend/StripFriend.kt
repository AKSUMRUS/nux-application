package com.ledokol.thebestproject.ui.components.molecules.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.user.User
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.ui.components.atoms.Search
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH6

@Composable
fun StripFriend(
    user: User,
    addFriend: Boolean = false,
    onClick: () -> Unit = {},
    onRemoveFriend: () -> Unit = {},
){

    if(addFriend){
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(80.dp))
                .background(MaterialTheme.colors.secondary)
                .clickable { onClick() }
        ){
            HeadlineH6(
                text = stringResource(id = R.string.add_friend),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 15.dp, top = 7.dp, bottom = 7.dp, end = 15.dp)
            )
        }
    }else{
        if(user.status.in_app){
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(80.dp))
                    .background(MaterialTheme.colors.secondary)
            ){
                HeadlineH6(
                    text = user.status.app!!.name,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(start = 15.dp, top = 7.dp, bottom = 7.dp, end = 15.dp)
                    ,
                )
            }
        }else{
            HeadlineH6(
                text = "Свободен",
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 10.dp),
                color = MaterialTheme.colors.secondaryVariant,
            )
        }
        ButtonFull(
            text = stringResource(id = R.string.remove_friend),
            onClick = {
                onRemoveFriend()
            }
        )
    }

}