package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.molecules.GameInQuickGames
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickGameScreen(){
    val context = LocalContext.current
    val games = GamesStatistic.getInstalledAppGamesList(context.packageManager)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(
                listOf(MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary),
                angle = 105f
            )
//            .verticalScroll(remem berScrollState())
    ) {
        GridGames(games)
//        LazyColumn(
//            modifier = Modifier.fillMaxSize().padding(start = 40.dp, end = 40.dp),
//            content =
//            {
//                item {
//                    HeadlineH4(
//                        text = "Быстрая игра",
//                        fontWeight = FontWeight.W700,
//                        modifier = Modifier.padding(top=110.dp),
//                    )
//                    Body1(
//                        text = "Выберите игру, в которую вы пойдете играть",
//                        type = "background"
//                    )
//                }
//
//                items(games) { game ->
//                    GameInQuickGames(
//                        packageName = "fdfdfd",
//                        icon = GamesStatistic.getIcon(
//                            packageManager = context.packageManager,
//                            app = game,
//                        ),
//                        backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous)
//                    )
//                }
//            }
//        )
    }
}

val GreenGfg = Color(0xFF0F9D58)

@ExperimentalFoundationApi
@Composable
fun GridGames(games: List<ApplicationInfo>) {
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 120.dp, start = 20.dp, end = 20.dp),
        modifier = Modifier,
        ) {
            item (span = { GridItemSpan(2) },
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    HeadlineH4(
                        text = stringResource(id = R.string.nav_quick_game),
                        fontWeight = FontWeight.W700,
                    )
                    Body1(
                        text = stringResource(id = R.string.description_quick_game),
                        type = "background"
                    )
                }
            }

            items(games) { game ->
                GameInQuickGames(
                    packageName = "fdfdfd",
                    icon = GamesStatistic.getIcon(
                        packageManager = context.packageManager,
                        app = game,
                    ),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous)
                )
            }
        }
}


fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Context.log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}