package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.ui.components.molecules.GameInQuickGames
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuickGameScreen(
    viewModel: GamesViewModel
){
    val context = LocalContext.current
    val games = viewModel.state.games
//    val games = GamesStatistic.getInstalledAppGamesList(context.packageManager)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(remem berScrollState())
    ) {
        games?.let { GridGames(it) }
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
fun GridGames(games: List<Game>) {
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
                    ScreenTitle(name = stringResource(id = R.string.nav_quick_game), description = stringResource(id = R.string.description_quick_game))
                }
            }

            items(games) { game ->
                GameInQuickGames(
                    packageName = "fdfdfd",
                    icon = GamesStatistic.getIcon(
                        game = game,
                    ),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous)
                )
            }
        }
}


fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}