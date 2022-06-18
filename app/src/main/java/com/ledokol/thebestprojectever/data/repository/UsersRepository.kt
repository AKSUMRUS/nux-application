package com.ledokol.thebestprojectever.data.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao
import com.ledokol.thebestprojectever.data.local.user.UsersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class UsersRepository(
    private val usersDao: UsersDao
) {
    val users: LiveData<List<User>> = usersDao.getUsers()


    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertUser(user : User){
        coroutineScope.launch {
            usersDao.insertUser(user)
        }
    }

    fun clearUsers(){
        coroutineScope.launch {
            usersDao.clearUsers()
        }
    }

    fun getUser(id: Int){
        coroutineScope.launch {

        }
    }

}