package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GamesDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor(
    private val api: RetrofitServices,
    private val dao: GamesDao
) {

    fun insertGame(game: Game){
        dao.insertGame(game)
    }

    fun clearGames(){
        dao.clearGames()
    }

    fun getGames(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Game>>> {
        return flow{
            emit(Resource.Loading(true))
            val localGames = dao.getGames(query)
            emit(Resource.Success(
                data = localGames
            ))
            return@flow

        }
    }

}