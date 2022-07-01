package com.ledokol.thebestprojectever.ui.components.molecules

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.screens.GameProfile

@Composable
fun UserOverviewProfile(gameProfiles: List<GameProfile>){

    val context: Context = LocalContext.current
    val packageManager = context.packageManager


    LazyColumn(
        content = {
            items(gameProfiles){ game ->
                GameInList(
                    name = game.getName(context,packageManager),
                    icon = game.getIcon(context, packageManager),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                )
            }
        }
    )

}