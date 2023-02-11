package com.nux.studio.dvor.data.local.game

import android.content.Context
import com.nux.studio.dvor.domain.games.StatusJSON

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
