package com.ledokol.thebestproject.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GamesDao
import com.ledokol.thebestproject.data.local.user.Apps
import com.ledokol.thebestproject.data.remote.RetrofitServices
import com.ledokol.thebestproject.domain.games.AppsStatus
import com.ledokol.thebestproject.domain.games.GameJSON
import com.ledokol.thebestproject.domain.games.StatusJSON
import com.ledokol.thebestproject.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val api: RetrofitServices,
    private val dao: GamesDao
) {

    private fun convertBitmapToString(bitmap: Bitmap): String {
        return bitmap.asImageBitmap().toString()
    }



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
        games: List<ApplicationInfo>,
        packageManager: PackageManager,
    ) {
        for(game in games){
            game.packageName.let { packageName ->
                val icon = packageManager.getApplicationIcon(packageName).toBitmap()
                val out = convertBitmapToPNG(icon)

                val requestBody: RequestBody = RequestBody.create("image/png".toMediaTypeOrNull(),out)
                val icon_preview: MultipartBody.Part = MultipartBody.Part.createFormData("icon_preview", "icon_preview.png", requestBody)

//                val iconString = convertBitmapToString(icon)

                val pushGamesIconsCall = api.pushGamesIcon(
                    package_name = game.packageName,
                    icon_preview = icon_preview
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
                            Log.d("GamesRepository", "Failed to push game icon")
                        }
                    }

                    override fun onFailure(
                        call: Call<Any>,
                        t: Throwable
                    ) {
                        Log.d("GamesRepository", "Failed to push game icon")
                    }
                })
            }
        }
    }

    fun getGames(
        id: String
    ): Flow<Resource<Apps> > {
        return flow{
            emit(Resource.Loading(true))

//            val remoteGames = dao.getGames("")
            val remoteGames = try{
                val gamesCall = api.getUserGames(id)
                val myResponse: Apps? = gamesCall.awaitResponse().body()

                myResponse

            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteGames?.let { games ->
                dao.clearGames()

                emit(Resource.Success(
                    data = games
                ))

                emit(Resource.Loading(false))
            }
        }
    }

    fun shareGames(
        games: List<StatusJSON>,
    ): Flow<Resource<List<Game>?>> {
        return flow {
            Log.e("shareGames", AppsStatus(games).toString())
            val ans = api.shareGames(games = AppsStatus(games)).awaitResponse().body()

            //ans?.send_icons_apps_ids

            Log.e("shareGames", ans.toString())
            dao.insertGames(fromGameJSONToGame(ans?.apps))
            emit(Resource.Success(
                data = fromGameJSONToGame(ans?.apps)
            ))
        }
    }

//    private fun fromGameToStatusJSON(games: List<Game>): List<StatusJSON> {
//        val res: MutableList<StatusJSON> = mutableListOf()
//        for (game in games){
//            res.add(StatusJSON(game.android_package_name,game.name,game.category))
//        }
//        return res.toList()
//    }

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