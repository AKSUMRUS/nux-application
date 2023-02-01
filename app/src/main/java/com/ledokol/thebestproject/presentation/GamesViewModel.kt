package com.ledokol.thebestproject.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GameState
import com.ledokol.thebestproject.data.local.game.GamesEvent
import com.ledokol.thebestproject.data.repository.GamesRepository
import com.ledokol.thebestproject.domain.games.StatusJSON
import com.ledokol.thebestproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
) : ViewModel() {

    var state by mutableStateOf(GameState())

    fun onEvent(event: GamesEvent) {
        when (event) {
            is GamesEvent.PushGamesIcons -> {
                pushGamesIcons(
                    games = event.games,
                    context = event.context,
                )
            }

            is GamesEvent.ShareGames -> {
                shareGames(games = event.games, context = event.context)
            }

            is GamesEvent.GetMyGames -> {
                getMyGames()
            }
        }
    }

    fun clearGames() {
        viewModelScope.launch {
            repository.clearGames()
        }
    }

    fun getGame(
        query: String = state.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            repository.getGame(query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { game ->
                                game?.let { setSelectedGame(it) }
                            }
                            Log.e("getGame_ViewModel", state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                }
        }
    }

    private fun pushGamesIcons(
        games: List<String>?,
        context: Context,
    ) {
        viewModelScope.launch {
            repository.pushGamesIcons(
                games = games,
                context = context,
            )
        }
    }

    fun setSelectedGame(game: Game) {
        state = state.copy(
            game = game
        )
    }

    fun shareGames(
        games: List<StatusJSON>,
        context: Context,
    ) {
        viewModelScope.launch {
            repository.shareGames(games)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                games = result.data!!.apps
                            )

                            getMyGames()

                            Log.e(
                                "shareGames_ViewModel",
                                result.data.send_icons_apps_ids.toString()
                            )

                            pushGamesIcons(result.data.send_icons_apps_ids, context)
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }

    private fun getMyGames() {
        viewModelScope.launch {
            repository.getMyGames()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { games ->
                                if (!games.isNullOrEmpty()) {
                                    val myGames: MutableList<Game> = mutableListOf()
                                    for (game in games) {
                                        myGames.add(
                                            Game(
                                                android_package_name = game.app.android_package_name,
                                                name = game.app.name,
                                                id = game.app.id,
                                                category = game.app.category,
                                                icon_preview = game.app.icon_preview,
                                                image_wide = game.app.image_wide,
                                                icon_large = game.app.icon_large,
                                                installed = game.statistics.installed,
                                                activity_last_two_weeks = game.statistics.activityLastTwoWeeks,
                                                activity_total = game.statistics.activityTotal,
                                                dt_last_activity = game.statistics.lastActivityDate,
                                            )
                                        )
                                    }
                                    state = state.copy(
                                        games = myGames,
                                    )
                                }
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }
}
