package com.nux.studio.dvor.data.repository

import android.util.Log
import com.nux.studio.dvor.data.error.ErrorRemote
import com.nux.studio.dvor.data.local.user.Apps
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.data.local.user.UsersDao
import com.nux.studio.dvor.data.remote.RetrofitServices
import com.nux.studio.dvor.domain.profile.ExistsUserJSON
import com.nux.studio.dvor.domain.users.AddFriend
import com.nux.studio.dvor.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val api: RetrofitServices,
    private val dao: UsersDao,
) : BasicRepository() {

    fun getUserByNickname(nickname: String): Flow<Resource<User>> {
        return doSafeWork(
            doAsync = {
                val getUser = api.getUserByNickname(nickname)

                val myResponse = getUser.awaitResponse()

                myResponse
            },
            getResult = { user ->
                user.body()
            }
        )
    }

    fun getUserByPhone(phone: String): Flow<Resource<User>> {
        return doSafeWork(
            doAsync = {
                val getUser = api.getUserByPhone(phone)

                val myResponse = getUser.awaitResponse()

                myResponse
            },
            getResult = { user ->
                user.body()
            }
        )
    }

    fun getFriends(
        fetchFromRemote: Boolean,
        query: String,
        shouldReload: Boolean
    ): Flow<Resource<List<User>>> {
        return flow {
            if (shouldReload) {
                emit(Resource.Loading(true))
            }
            val localUsers = dao.getUsers(query)
            emit(
                Resource.Success(
                    data = localUsers
                )
            )

            val isDbEmpty = localUsers.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteUsers = try {
                val usersCall = api.getFriends()
                val myResponse: List<User>? = usersCall.awaitResponse().body()

                myResponse
            } catch (e: Exception) {
                emit(Resource.Error(ErrorRemote.NoInternet))
                null
            }

            Log.e("USERS", remoteUsers.toString())

            remoteUsers?.let { users ->
                dao.clearUsers()
                dao.insertUsers(
                    users
                )
                Log.e("DAO Users", dao.getUsers(query).toString())
                emit(
                    Resource.Success(
                        data = dao.getUsers(query)
                    )
                )
            }
            emit(Resource.Loading(false))
        }
    }

    fun addFriend(
        friendId: String
    ): Flow<Resource<String>> {
        return doSafeWork(
            doAsync = {
                val addFriend = api.addFriend(
                    addFriend = AddFriend(user_id = friendId)
                )

                val myResponse = addFriend.awaitResponse()

                myResponse
            },
            getResult = { response ->
                response.body()
            }
        )
    }

    fun getUser(id: String): Flow<Resource<User>> {
        return doSafeWork(
            doAsync = {
                val friendCall = api.getUser(id)
                val myResponse = friendCall.awaitResponse()
                myResponse
            },
            getResult = { friend ->
                friend.body()
            }
        )
    }

    fun getUserGames(id: String): Flow<Resource<List<Apps>>> {
        return doSafeWork(
            doAsync = {
                val gamesCall = api.getUserGames(id)
                val myResponse = gamesCall.awaitResponse()
                myResponse
            },
            getResult = { games ->
                games.body()?.apps_and_stats
            }
        )
    }

    fun checkExistsNickname(
        nickname: String
    ): Flow<Resource<ExistsUserJSON>> {
        return doSafeWork(
            doAsync = {
                val callExistsUser = api.checkExistsNickname(
                    nickname = nickname
                )
                callExistsUser.awaitResponse()
            },
            getResult = { checkUser ->
                checkUser.body()
            }
        )
    }


    fun checkExistsPhone(
        phone: String
    ): Flow<Resource<ExistsUserJSON>> {
        return doSafeWork(
            doAsync = {
                val callExistsUser = api.checkExistsPhone(
                    phone = phone
                )
                callExistsUser.awaitResponse()
            },
            getResult = { checkUser ->
                checkUser.body()
            }
        )
    }

    fun getUsersFindFriend(
        fetchFromRemote: Boolean,
        query: String,
        accessToken: String,
        shouldReload: Boolean
    ): Flow<Resource<List<User>>> {
        return flow {
            if (shouldReload) {
                emit(Resource.Loading(true))
            }
            val localUsers = dao.getUsers(query)
            emit(
                Resource.Success(
                    data = localUsers
                )
            )

            val isDbEmpty = localUsers.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteUsers = try {
                val usersCall = api.getFindFriends(authHeader = "Bearer $accessToken")
                val myResponse: List<User>? = usersCall.awaitResponse().body()

                myResponse
            } catch (e: Exception) {
                emit(Resource.Error(ErrorRemote.NoInternet))
                null
            }

            Log.e("USERS", remoteUsers.toString())

            remoteUsers?.let { users ->
                dao.clearUsers()
                dao.insertUsers(
                    users
                )
                Log.e("DAO Users", dao.getUsers(query).toString())
                emit(
                    Resource.Success(
                        data = dao.getUsers(query)
                    )
                )
                emit(Resource.Loading(false))
            }

        }
    }

    fun removeFriend(
        friendId: String
    ): Flow<Resource<String>> {
        return doSafeWork(
            doAsync = {
                val removeFriend = api.removeFriend(friend_id = friendId)
                val myResponse = removeFriend.awaitResponse()
                myResponse
            },
            getResult = { response ->
                response.body().toString()
            }
        )
    }

    fun rejectInvite(
        userId: String
    ): Flow<Resource<String>> {
        return doSafeWork(
            doAsync = {
                val rejectInvite = api.rejectInvite(from_user_id = userId)

                val myResponse = rejectInvite.awaitResponse()

                myResponse
            },
            getResult = { response ->
                response.body()
            }
        )
    }

    fun getRecommendedFriends(

    ): Flow<Resource<List<User>>> {
        return doSafeWork(
            doAsync = {
                val recommendedFriends = api.getRecommendedFriends()

                val myResponse = recommendedFriends.awaitResponse()

                myResponse
            },
            getResult = { response ->
                response.body()
            }
        )
    }

}