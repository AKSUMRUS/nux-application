package com.nux.studio.dvor.domain.games

import com.nux.studio.dvor.data.local.game.Game

data class AppsGameResponse(
    val apps: List<Game>,
    val send_icons_apps_ids: List<String>? = listOf()
)