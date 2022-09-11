package com.ledokol.thebestproject.domain.games

import com.ledokol.thebestproject.data.local.game.Game

data class AppsGameResponse(
    val apps: List<Game>,
    val send_icons_apps_ids : List<String>? = listOf()
)