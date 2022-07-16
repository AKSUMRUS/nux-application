package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GamesDao
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.data.local.user.Status
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.AppsGame
import com.ledokol.thebestprojectever.domain.AppsStatus
import com.ledokol.thebestprojectever.domain.GameJSON
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.*
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
        accessToken: String
    ): Flow<Resource<List<Game>> > {
        return flow{
            emit(Resource.Loading(true))

            val remoteGames = dao.getGames("")
//            val remoteGames = try{
//                val gamesCall = api.getUserGames(accessToken)
//                val myResponse: Apps? = gamesCall.awaitResponse().body()
//
//                myResponse
//
//            } catch(e: IOException) {
//                e.printStackTrace()
//                emit(Resource.Error("Couldn't load data"))
//                null
//            } catch (e: HttpException) {
//                e.printStackTrace()
//                emit(Resource.Error("Couldn't load data"))
//                null
//            }

            emit(Resource.Success(
                data = remoteGames
            ))

            emit(Resource.Loading(false))
            return@flow
        }
    }

    fun shareGames(
        accessToken: String
    ) {
        val localGames = dao.getGames("")
        val localGamesJson: List<StatusJSON> = fromGameToStatusJSON(localGames)
        Log.e("ShareGames Repository",AppsStatus(localGamesJson).toString())
        api.shareGames(authHeader = "Bearer $accessToken", games = AppsStatus(localGamesJson)).enqueue(object : Callback<AppsGame> {
            override fun onResponse(
                call: Call<AppsGame>,
                response: Response<AppsGame>
            ) {
                Log.e("ShareGames ans", response.body()!!.toString())
                Log.e("SetStatusShareGames","Status has set")
            }
            override fun onFailure(call: Call<AppsGame>, t: Throwable) {
                Log.e("SetStatusShareGames","Error "+t.toString())
            }
        })
    }

    fun fromGameToStatusJSON(games: List<Game>): List<StatusJSON> {
        val res: MutableList<StatusJSON> = mutableListOf()
        for (game in games){
            res.add(StatusJSON(game.android_package_name,game.name,game.category.toString()))
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