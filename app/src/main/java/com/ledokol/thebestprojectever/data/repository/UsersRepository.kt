package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import com.ledokol.thebestprojectever.data.local.user.CurrentApp
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api : RetrofitServices,
    private val dao : UsersDao,
){
    fun insertUser(user: User) {
        dao.insertUser(user)
    }

    fun uploadImage(){
//        api.uploadImage()
    }

    fun getUsers(
        fetchFromRemote: Boolean,
        query: String,
        accessToken: String,
        shouldReload: Boolean
    ): Flow<Resource<List<User>>> {
        return flow{
            if(shouldReload) {
                emit(Resource.Loading(true))
            }
            val localUsers = dao.getUsers(query)
            emit(Resource.Success(
                data = localUsers
            ))

            val isDbEmpty = localUsers.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteUsers = try{
                val usersCall = api.getFriends(authHeader = "Bearer $accessToken")
                val myResponse: List<User>? = usersCall.awaitResponse().body()

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

            Log.e("USERS",remoteUsers.toString())

            remoteUsers?.let { users ->
                dao.clearUsers()
                dao.insertUsers(
                    users
                )
                Log.e("DAO Users",dao.getUsers(query).toString())
                emit(Resource.Success(
                    data = dao.getUsers(query)
                ))
                emit(Resource.Loading(false))
            }

        }
    }

    fun getUser(id: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            val friend = try {
                val friendCall = api.getUser(id)
                val myResponse: User? = friendCall.awaitResponse().body()

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
            Log.e("FRIEND",friend.toString())
            emit(Resource.Success(
                data = friend
            ))

            emit(Resource.Loading(false))
        }
    }

    fun getUserGames(id: String): Flow<Resource<List<CurrentApp>>> {
        return flow {
            emit(Resource.Loading(true))
            val games = try {
                val gamesCall = api.getUserGames(id)
                val myResponse: List<CurrentApp> = gamesCall.awaitResponse().body()!!.apps
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
            Log.e("FRIEND",games.toString())
            emit(Resource.Success(
                data = games
            ))
            emit(Resource.Loading(false))
        }
    }

}