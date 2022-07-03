package com.ledokol.thebestprojectever.data.local.game

import androidx.lifecycle.LiveData

data class GameState(
    val games: List<Game>? = mutableListOf(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
