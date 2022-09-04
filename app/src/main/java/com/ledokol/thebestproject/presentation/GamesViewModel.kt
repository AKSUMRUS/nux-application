package com.ledokol.thebestproject.presentation

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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
): ViewModel() {

    var state by mutableStateOf(GameState())

    fun onEvent(event: GamesEvent){
        when(event){
            is GamesEvent.PushGamesIcons -> {
                pushGamesIcons(
                    games = event.games,
                    packageManager = event.packageManager,
                    accessToken = event.accessToken
                )
            }
        }
    }

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

//    private fun insertGames(games: List<Game>){
//        for (game in games){
//            insertGame(game)
//        }
//    }
//
//    private fun fromAppsToListGames(apps: Apps): List<Game>{
//        val res:MutableList<Game> = mutableListOf()
//        for (app in apps.apps){
//            res.add(
//                Game(
//                    android_package_name = app.android_package_name,
//                    name = app.name,
//                    category = app.category,
//                    icon_preview = app.icon_preview,
//                    icon_large = app.icon_large,
//                    image_wide = app.image_wide,
//                    id = app.id
//                )
//            )
//        }
//
//        return res
//    }

//    fun getGames(
//        query: String = state.searchQuery,
//        id: String
//    ){
//        viewModelScope.launch {
//            repository.getGames(id)
//                .collect{ result ->
//                        when(result){
//                            is Resource.Success -> {
//                                result.data.let { games ->
//                                    val listGames = fromAppsToListGames(games!!)
//                                    insertGames(listGames)
//                                    state = state.copy(
//                                        games = listGames
//                                    )
//                                }
//                                Log.e("GAMES",state.toString())
//                            }
//                            is Resource.Error -> Unit
//                            is Resource.Loading -> {
//                                state = state.copy(
//                                    isLoading = result.isLoading
//                                )
//                            }
//                        }
//
//                }
//        }
//    }

    fun getGame(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getGame(query)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { game ->
                                game?.let { setSelectedGame(it) }
                            }
                            Log.e("getGame_ViewModel",state.toString())
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
        games: List<ApplicationInfo>,
        packageManager: PackageManager,
        accessToken: String
    ) {
        viewModelScope.launch {
            repository.pushGamesIcons(
                games = games,
                packageManager = packageManager,
                accessToken = accessToken
            )
        }
    }

    fun setSelectedGame(game: Game){
        state = state.copy(
            game = game
        )
    }

    fun shareGames(
        games: List<StatusJSON>,
        ){
        viewModelScope.launch {
            repository.shareGames(games)
                .collect{result ->
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
