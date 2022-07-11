package com.ledokol.thebestprojectever.ui.components.molecules

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.ui.components.screens.GameProfile
import com.ledokol.thebestprojectever.ui.components.screens.getIcon

@Composable
fun UserOverviewProfile(games: List<Game>?){

    val context: Context = LocalContext.current
    val packageManager = context.packageManager


    if(games!=null){
        LazyColumn(
            content = {
                items(games){ game ->
                    GameInList(
                        name = game.name,
                        icon = getIcon(context, packageManager, game.android_package_name)!!.asImageBitmap(),
                        backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                    )
                }
            }
        )

    }

}