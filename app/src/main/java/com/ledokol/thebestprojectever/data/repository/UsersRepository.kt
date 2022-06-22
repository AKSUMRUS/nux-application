package com.ledokol.thebestprojectever.data.repository

import android.text.BoringLayout
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao
import com.ledokol.thebestprojectever.data.local.user.UsersDatabase
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

interface UsersRepository{

    fun insertUser(
        user: User
    )

    fun getUsers(
        fetchFromRemote: Boolean,
        query: String = ""
    ): Flow<Resource<List<User>>>

    fun getUser(
        id: Int
    ): Resource<User>

}