package com.ledokol.thebestprojectever.ui.components.screens.friends

import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.UserViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.molecules.BackToolbar
import com.ledokol.thebestprojectever.ui.components.molecules.GameInList
import com.ledokol.thebestprojectever.ui.components.molecules.UserInformationProfile
import com.ledokol.thebestprojectever.ui.components.molecules.*

@Composable
fun FriendScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    gamesViewModel: GamesViewModel,
){

    val user = userViewModel.state.friendUser
    val state = userViewModel.state

    if(user != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                )
//            .verticalScroll(rememberScrollState())
        ) {
            BackToolbar (
                buttonBackClick = {
                    navController.popBackStack()
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
//                contentPadding = PaddingValues(top = 0.dp, start = 20.dp, end = 20.dp),
                modifier = Modifier,
            ){
                item(
                    span = { GridItemSpan(1) },
                ) {
                    Column(){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 120.dp, bottom = 10.dp)
                        ){
                            Column(
                                modifier = Modifier
                                    .weight(2f),
                            ){
                                HeadlineH4(
                                    text = user.nickname,
                                    fontWeight = FontWeight.W700,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Body1(
                                        text = if (user.status.finished) "Offline" else "Online"
                                    )

                                    Status(
                                        status = if(user.status.finished)"offline" else "online",
                                        modifier = Modifier
                                            .align(CenterVertically)
                                            .fillMaxHeight()
                                            .padding(start = 10.dp)
                                            .align(Bottom)
                                        ,
                                    )
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp)
                                    .weight(1f)
                                ,
                            ){

                                Image(
                                    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                                    contentDescription = "Аноним",
                                    modifier = Modifier
                                        .size(height = 120.dp, width = 120.dp)
                                        .align(CenterVertically),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                }

                if(state.friendUser!!.status.current_app != null) {
                    item(
                        span = { GridItemSpan(2) },
                    ) {
                        HeadlineH5(
                            text = stringResource(id = R.string.latest_activity),
                            modifier = Modifier
                                .padding(start = 20.dp),
                            fontWeight = W700
                        )

                        state.friendUser.status.current_app.let { game ->
                            GameActivity(
                                packageName = game!!.android_package_name,
                                icon = game.icon_preview,
                                iconLarge = game.icon_large,
                                backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                                startTime = state.friendUser.status.started_at,
                                finishTime = state.friendUser.status.last_update
                            )
                        }
                    }
                }


                if(state.games != null) {
                    item(
                        span = { GridItemSpan(1) },
                    ) {
                        Column() {
                            HeadlineH5(
                                text = stringResource(id = R.string.games),
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                fontWeight = W700
                            )
                        }
                    }

                    items(state.games!!) { game ->
                        GameInQuickGamesFriend(
                            packageName = game.android_package_name,
                            icon = game.icon_preview,
                            iconLarge = game.icon_large,
                            backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous),
                        )
                    }
                }
            }
        }


    }
}