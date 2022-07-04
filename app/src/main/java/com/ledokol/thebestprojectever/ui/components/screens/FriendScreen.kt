package com.ledokol.thebestprojectever.ui.components.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH3

@Composable
fun FriendScreen(
    userViewModel: UserViewModel
){

    val state = userViewModel.state

    Log.e("FRiend",state.toString())
    if(state.friendUser != null) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 120.dp, bottom = 10.dp)
                .padding(10.dp)
        ) {
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                contentDescription = "Аноним",
                modifier = Modifier
                    .heightIn(max = 50.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
                tint = Color.Unspecified,
            )
            HeadlineH3(
                text = state.friendUser.nickname,

                )
        }
    }
}