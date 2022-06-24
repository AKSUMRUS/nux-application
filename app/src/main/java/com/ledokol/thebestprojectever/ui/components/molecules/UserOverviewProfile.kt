package com.ledokol.thebestprojectever.ui.components.molecules

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.screens.Game

@Composable
fun UserOverviewProfile(games: List<Game>){

    val context: Context = LocalContext.current
    val packageManager = context.packageManager


    LazyColumn(
        content = {
            items(games){ game ->
                GameInList(
                    name = game.getName(context, packageManager),
                    icon = game.getIcon(context, packageManager),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                )
            }
        }
    )

}