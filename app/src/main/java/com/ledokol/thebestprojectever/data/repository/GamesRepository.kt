package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GamesDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.GameJSON
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
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
            emit(Resource.Loading(false))
            return@flow
        }
    }

    fun shareGames() {
        val localGames = dao.getGames("")
        val localGamesJson: List<StatusJSON> = fromGameToStatusJSON(localGames)
        Log.e("ShareGames Repository",localGames.toString())
        api.shareGames(localGamesJson).enqueue(object : Callback<List<GameJSON> > {
            override fun onResponse(
                call: Call<List<GameJSON>>,
                response: Response<List<GameJSON>>
            ) {
                Log.e("SetStatus","Status has set")
            }

            override fun onFailure(call: Call<List<GameJSON>>, t: Throwable) {
                Log.e("SetStatus",t.toString())
            }

        })
    }

    fun fromGameToStatusJSON(games: List<Game>): List<StatusJSON> {
        val res: MutableList<StatusJSON> = mutableListOf()
        for (game in games){
            res.add(StatusJSON(game.gamePackage,game.name,game.category.toString()))
        }
        return res.toList()
    }

    fun getGame(packageName: String): Flow<Resource<Game>> {
        return flow {
            emit(Resource.Loading(true))
            val game = try {
                dao.getGame(packageName)
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            Log.e("Game",game.toString())
            emit(Resource.Success(
                data = game
            ))

            emit(Resource.Loading(false))
        }
    }


}