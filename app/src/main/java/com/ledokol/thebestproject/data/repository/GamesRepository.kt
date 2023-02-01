package com.ledokol.thebestproject.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.ledokol.thebestproject.data.error.ErrorRemote
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GamesDao
import com.ledokol.thebestproject.data.local.user.Apps
import com.ledokol.thebestproject.data.remote.RetrofitServices
import com.ledokol.thebestproject.domain.games.AppsGameResponse
import com.ledokol.thebestproject.domain.games.AppsStatus
import com.ledokol.thebestproject.domain.games.GameJSON
import com.ledokol.thebestproject.domain.games.StatusJSON
import com.ledokol.thebestproject.domain.statistics.GameSessionStatisticsList
import com.ledokol.thebestproject.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor(
    private val api: RetrofitServices,
    private val dao: GamesDao
) : BasicRepository() {

    fun insertGame(game: Game){
        dao.insertGame(game)
    }

    fun clearGames(){
        dao.clearGames()
    }

    private fun convertBitmapToPNG(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun pushGamesIcons(
        games: List<String>?,
        context: Context,
    ) {
        Log.e("pushGamesIcons",games.toString())

        if(games == null){
            return
        }

        for(game in games){
            game.let { packageName -> // на разбор
                try {
                    val icon = context.packageManager.getApplicationIcon(packageName).toBitmap()
                    val out = convertBitmapToPNG(icon)

                    val requestBody: RequestBody =
                        RequestBody.create("image/png".toMediaTypeOrNull(), out)
                    val iconPreview: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "icon_preview",
                        "icon_preview.png",
                        requestBody
                    )

                    Log.e("pushGamesIcons", "$packageName $iconPreview")

                    val pushGamesIconsCall = api.pushGamesIcon(
                        package_name = packageName,
                        icon_preview = iconPreview
                    )

                    pushGamesIconsCall.enqueue(object : Callback<Any> {
                        override fun onResponse(
                            call: Call<Any>,
                            response: Response<Any>
                        ) {
                            Log.d("GamesRepository", "Successfully pushed game icon")
                            if (response.isSuccessful) {
                                Log.d("GamesRepository", "Successfully pushed game icon")
                            } else {
                                Log.d("GamesRepository", "Failed to push game icon ${response.errorBody()}")
                            }
                        }

                        override fun onFailure(
                            call: Call<Any>,
                            t: Throwable
                        ) {
                            Log.d("GamesRepository", "Failed to push game icon ${t.toString()}")
                        }
                    })
                }
                catch (e: Exception){
                    Log.e("GamesRepository", "Failed to push game icon $e")
                }
            }
        }
    }

    fun shareGames(
        games: List<StatusJSON>,
    ): Flow<Resource<AppsGameResponse>> {
        return flow {
            emit(Resource.Loading(true))
            Log.e("shareGames", AppsStatus(games).toString())
            val ans =
                try {
                    api.shareGames(games = AppsStatus(games)).awaitResponse().body()
                } catch(e: Exception) {
                    emit(Resource.Error(ErrorRemote.NoInternet))
                    null
                }

            //ans?.send_icons_apps_ids

            Log.e("shareGames", "Ответ: ${ans.toString()}")
            dao.insertGames(fromGameJSONToGame(ans?.apps))
            emit(Resource.Success(
                data = AppsGameResponse(
                    apps = fromGameJSONToGame(ans?.apps),
                    send_icons_apps_ids = ans?.send_icons_apps_ids ?: listOf()
                )
            ))
            emit(Resource.Loading(false))
        }
    }

    private fun fromGameJSONToGame(games: List<GameJSON>?): List<Game> {
        val res: MutableList<Game> = mutableListOf()
        if(games != null) {
            for (game in games) {
                res.add(
                    Game(
                        android_package_name = game.android_package_name,
                        name = game.name,
                        category = game.category,
                        icon_preview = game.icon_preview,
                        image_wide = game.image_wide,
                        icon_large = game.icon_large,
                        id = game.id
                    )
                )
            }
        }
        return res.toList()
    }

    fun getGame(
        packageName: String,
    ): Flow<Resource<Game>> {
        return flow {
            emit(Resource.Loading(true))
            val game = try {
                Log.e("PACKAGE GAME",packageName)
                dao.getGame(packageName)
            } catch(e: Exception) {
                emit(Resource.Error(ErrorRemote.NoInternet))
                null
            }
            Log.e("Game",game.toString())
            emit(Resource.Success(
                data = game
            ))

            emit(Resource.Loading(false))
        }
    }

    fun getMyGames() : Flow<Resource<List<Apps>>> {
        return doSafeWork(
            doAsync = {
                val gamesCall = api.getMyGames()
                val myResponse = gamesCall.awaitResponse()
                myResponse
            },
            getResult = { games ->
                Log.i("GamesRepository", "getMyGames: ${games.body()}")
                games.body()?.apps_and_stats
            }
        )
    }

}