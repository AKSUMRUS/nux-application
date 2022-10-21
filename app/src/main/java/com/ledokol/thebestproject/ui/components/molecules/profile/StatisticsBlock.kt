package com.ledokol.thebestproject.ui.components.molecules.profile

import android.app.usage.UsageStats
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.molecules.games.GameStat

@Composable
fun StatisticsBlock(
    games: List<Game>? = listOf(),
    onClickGame: (Game) -> Unit,
    stats: Map<String, UsageStats>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ,
    ) {

        LazyColumn(

        ){
            if (games != null) {
                item(){
                    HeadlineH4(
                        text = "Игры",
                        modifier = Modifier.padding(start = 0.dp, bottom = 10.dp),
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W700,
                    )
                }

                items(games) { game ->
                    GameStat(
                        packageName = game.android_package_name,
                        name = game.name,
                        icon = game.icon_preview!!,
                        openGame = true,
                        onClick = {
                                  onClickGame(game)
                        },
                        usageTime = if(game.android_package_name in stats.keys)
//                            (stats.get(game.android_package_name)!!.totalTimeInForeground.milliseconds).toString()
                            (stats.get(game.android_package_name)!!.totalTimeInForeground.toInt()/60000)
//                            null
                        else 0
                    )
                }
            }
        }


//        Box(
//            Modifier
//                .padding(top = 10.dp)
//                .clip(RoundedCornerShape(7.dp))
//                .background(color = MaterialTheme.colors.primary)
//                .fillMaxHeight()
//                .padding(10.dp)
//        ) {
//                HeadlineH5(
//                    text = "Скоро здесь будет ваша \n" +
//                            "игровая статистика!\n" +
//                            "Но мы пока её не запрогали :)",
//                    textAlign = TextAlign.Center,
//                )
//        }
    }
}