package com.ledokol.thebestprojectever.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GameState
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.data.repository.GamesRepository
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
): ViewModel() {

    var state by mutableStateOf(GameState())

    fun clearGames(){
        viewModelScope.launch {
            repository.clearGames()
        }
    }

    private fun insertGame(game : Game){
        viewModelScope.launch {
            repository.insertGame(game)
        }
    }

    fun insertGames(games: List<Game>){
        for (game in games){
            insertGame(game)
        }
    }

    private fun fromAppsToListGames(apps: Apps): List<Game>{
        val res:MutableList<Game> = mutableListOf()
        for (app in apps.apps){
            res.add(
                Game(
                    android_package_name = app.android_package_name,
                    name = app.name,
                    category = app.category,
                    icon_preview = app.icon_preview,
                    icon_large = app.icon_large,
                    image_wide = app.image_wide,
                    id = app.id
                )
            )
        }

        return res
    }

    fun getGames(
        query: String = state.searchQuery,
        id: String
    ){
        viewModelScope.launch {
            repository.getGames(id)
                .collect{ result ->
                        when(result){
                            is Resource.Success -> {
                                result.data.let { games ->
                                    val listGames = fromAppsToListGames(games!!)
                                    insertGames(listGames)
                                    state = state.copy(
                                        games = listGames
                                    )
                                }
                                Log.e("GAMES",state.toString())
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

    fun getGame(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getGame(query)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { game ->
                                state = state.copy(
                                    game = game
                                )
                            }
                            Log.e("GAME",state.toString())
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

    fun shareGames(
        games: List<StatusJSON>,
        accessToken: String,
        ){
        viewModelScope.launch {
            repository.shareGames(games, accessToken)
                .collect(){result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                games = result.data
                            )
                        }
                        is Resource.Loading -> Unit
                        is Resource.Error -> Unit
                    }
                }
        }
    }
}
