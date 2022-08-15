package com.ledokol.thebestprojectever.data.repository

import android.util.Log
import com.ledokol.thebestprojectever.data.local.user.CurrentApp
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.profile.ExistsUserJSON
import com.ledokol.thebestprojectever.domain.users.AddFriend
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
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

    fun getUserByNickname(nickname: String): Flow<Resource<User> > {
        return flow{
            emit(Resource.Loading(true))

            val user = try {
                val getUser = api.getUserByNickname(nickname)

                val myResponse = getUser.awaitResponse().body()

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

            Log.e("addFrined", user.toString())

            user?.let {
                emit(Resource.Success(
                    data = user
                ))
            }

            emit(Resource.Loading(false))
        }
    }

    fun getUserByPhone(phone: String): Flow<Resource<User> > {
        return flow{
            emit(Resource.Loading(true))

            val user = try {
                val getUser = api.getUserByPhone(phone)

                val myResponse = getUser.awaitResponse().body()

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

            user?.let {
                emit(Resource.Success(
                    data = user
                ))
            }

            emit(Resource.Loading(false))
        }
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
            }
            emit(Resource.Loading(false))
        }
    }

    fun addFriend(
        accessToken: String,
        friendId: String
    ): Flow<Resource<String> > {
        return flow{
            emit(Resource.Loading(true))

            Log.e("addFriendRepository", "$friendId $accessToken")

            val response = try{
                val addFriend = api.addFriend(
                    authHeader = "Bearer $accessToken",
                    addFriend = AddFriend(user_id = friendId)
                )

                val myResponse: String? = addFriend.awaitResponse().body()

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

            emit(Resource.Success(
                data = response
            ))
            emit(Resource.Loading(false))
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
            Log.e("FRIEND_GAMES",games.toString())
            emit(Resource.Success(
                data = games
            ))
            emit(Resource.Loading(false))
        }
    }

    fun checkExistsNickname(
        nickname: String
    ): Flow<Resource<ExistsUserJSON>>{
        return flow{
            emit(Resource.Loading(true))
            val TAG = "checkExistsNickname"
            Log.e(TAG, "start $nickname")
            val callExistsUser = api.checkExistsNickname(
                nickname = nickname
            )

            val checkUser = try{
                callExistsUser.awaitResponse().body()
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            checkUser?.let{
                emit(Resource.Success(
                    data = checkUser
                ))
            }
            emit(Resource.Loading(false))
        }
    }


    fun checkExistsPhone(
        phone: String
    ): Flow<Resource<ExistsUserJSON>>{
        return flow{
            emit(Resource.Loading(true))
            val TAG = "checkExistsPhone"
            Log.e(TAG, "start $phone")
            val callExistsUser = api.checkExistsPhone(
                phone = phone
            )

            val checkUser = try{
                callExistsUser.awaitResponse().body()
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            checkUser?.let{
                emit(Resource.Success(
                    data = checkUser
                ))
            }
            emit(Resource.Loading(false))
        }
    }

    fun getUsersFindFriend(
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
                val usersCall = api.getFindFriends(authHeader = "Bearer $accessToken")
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

}