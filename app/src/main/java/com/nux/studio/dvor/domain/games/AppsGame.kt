package com.nux.studio.dvor.domain.games

data class AppsGame(
    val apps: List<GameJSON>,
    val send_icons_apps_ids: List<String>
)