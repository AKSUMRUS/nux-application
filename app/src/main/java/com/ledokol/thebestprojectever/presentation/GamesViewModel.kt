package com.ledokol.thebestprojectever.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GameState
import com.ledokol.thebestprojectever.data.repository.GamesRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
): ViewModel() {

    var state by mutableStateOf(GameState())

    init {
        getGames()
    }

    fun clearGames(){
        viewModelScope.launch {
            repository.clearGames()
        }
    }

    fun insertGame(game : Game){
        viewModelScope.launch {
            repository.insertGame(game)
        }
    }

    fun insertGames(games: List<Game>){
        for (game in games){
            insertGame(game)
        }
    }


    fun getGames(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getGames(false,query)
                .collect{ result ->
                        when(result){
                            is Resource.Success -> {
                                result.data.let { games ->
                                    state = state.copy(
                                        games = games
                                    )
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