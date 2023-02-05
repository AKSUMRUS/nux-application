package com.ledokol.dvor.domain.games

import com.ledokol.dvor.data.local.game.Game

data class AppsGameResponse(
    val apps: List<Game>,
    val send_icons_apps_ids: List<String>? = listOf()
)