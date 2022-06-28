package com.ledokol.thebestprojectever.data.repository

import android.provider.ContactsContract
import android.util.Log
import androidx.room.Insert
import com.ledokol.thebestprojectever.data.local.MyDatabase
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
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
class UsersRepository @Inject constructor(
    private val api : RetrofitServices,
    private val dao : UsersDao
){
    fun insertUser(user: User) {
        dao.insertUser(user)
    }

    fun clearUsers(){
        dao.clearUsers()
    }

    fun getUsers(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<User>>> {
        return flow{
            emit(Resource.Loading(true))
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
                val usersCall = api.getFriends()
                var myResponse: List<User> = emptyList()
                usersCall.enqueue(object : Callback<List<User>> {
                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        if (response.isSuccessful) {
                            Log.e("USERS",response.body().toString())
                            myResponse = response.body()!!
                        }
                        else{
                            Log.e("Users",response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Log.e("Users",t.toString())
                    }
                })

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

            remoteUsers?.let { users ->
                dao.clearUsers()
                dao.insertUsers(
                    users
                )
                emit(Resource.Success(
                    data = dao.getUsers("")
                ))
                emit(Resource.Loading(false))
            }

        }
    }

    fun getUser(id: Int): Resource<User> {
        TODO("Not yet implemented")
    }

}