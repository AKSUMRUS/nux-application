package com.ledokol.thebestprojectever.data.local.game

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

sealed class GamesEvent{
    class PushGamesIcons(
        val games: List<ApplicationInfo>,
        val packageManager: PackageManager,
        val accessToken: String
    ) : GamesEvent()
}
