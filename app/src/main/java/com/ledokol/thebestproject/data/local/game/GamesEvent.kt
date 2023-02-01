package com.ledokol.thebestproject.data.local.game

import android.content.Context
import com.ledokol.thebestproject.domain.games.StatusJSON

sealed class GamesEvent {
    class PushGamesIcons(
        val games: List<String>,
        val context: Context,
    ) : GamesEvent()

    class ShareGames(
        val games: List<StatusJSON>,
        val context: Context,
    ) : GamesEvent()

    object GetMyGames : GamesEvent()
}
