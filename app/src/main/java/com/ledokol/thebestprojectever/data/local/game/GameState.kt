package com.ledokol.thebestprojectever.data.local.game

import androidx.lifecycle.LiveData

data class GameState(
    val games: List<Game>? = mutableListOf(),
    val game: Game? = null, // выбранная игра для приглашения друзей
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
