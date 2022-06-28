package com.ledokol.thebestprojectever.data.local.game

data class GameState(
    val games: List<Game>? = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
