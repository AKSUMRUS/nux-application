package com.ledokol.thebestprojectever.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import com.ledokol.thebestprojectever.ui.navigation.TAG
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
UserViewModel @Inject constructor(
    private val repository: UsersRepository,
): ViewModel() {

    var accessToken: String = ""
    var state by mutableStateOf(UserState())

    private var searchUser: Job? = null

    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.Refresh -> {
                getUsers(
                    fetchRemote = true,
                    shouldReload = event.shouldReload
                )
                state = state.copy(isRefreshing = false)
            }
            is UserEvent.CheckExistsNickname -> {
                checkExistsNickname(event.nickname)
            }
            is UserEvent.CheckExistsPhone -> {
                checkExistsPhone(event.phone)
            }
            is UserEvent.AddFriend -> {
                addFriend(
                    accessToken = event.access_token,
                    nickname = event.nickname,
                    phone = event.phone,
                )
            }
            is UserEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchUser?.cancel()
                searchUser = viewModelScope.launch {
                    delay(500L)
                    getUsersFindFriend()
                }
            }
            is UserEvent.OnSearchQueryChangeFindFriend -> {
                state = state.copy(searchQuery = event.query)
                searchUser?.cancel()
                searchUser = viewModelScope.launch {
                    delay(500L)
                    getUsers()
                }
            }
            is UserEvent.GetFriendUser -> {
                getUser(id = event.id)
            }
            is UserEvent.SelectUser -> {
                viewModelScope.launch {
                    val shouldRemove = checkSelectedUser(event.user)
                    if (shouldRemove) {
                        removeSelectedUser(event.user)
                    } else {
                        insertSelectedUser(event.user)
                    }
                }
            }
            is UserEvent.GetFriendGames -> {
                getUsersGames(event.user)
            }
            is UserEvent.OpenScreen -> {
                openScreen(event.screen)
            }
        }
    }

    private fun openScreen(
        screen: String
    ){
        viewModelScope.launch {
            state = state.copy(
                openScreen = screen,
            )
        }
    }

    private suspend fun getUserByNickname(nickname: String){

            repository.getUserByNickname(nickname).collect{
                result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let{ user ->
                                state = state.copy(
                                    friendUser = user
                                )
                            }
//                            addFriendFinally(
//                                accessToken = accessToken,
//                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
            }
    }


    private suspend fun getUserByPhone(phone: String){

            repository.getUserByPhone(phone).collect{
                    result ->
                when(result){
                    is Resource.Success -> {
                        result.data.let{ user ->
                            state = state.copy(
                                friendUser = user
                            )
                        }
//                        addFriendFinally(
//                            accessToken = accessToken,
//                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }

    }


    fun insertUser(user: User){
        viewModelScope.launch{
            repository.insertUser(user)
            getUsers()
        }
    }

    fun uploadImage(){
        viewModelScope.launch {
            repository.uploadImage()
        }
    }

    fun addFriend(
        accessToken: String,
        nickname: String?,
        phone: String?
    ){
        Log.e("addFriend", "start")
        viewModelScope.launch {

//            withContext(Dispatchers.IO){
                val job = launch {
                    try {
                        Log.e(TAG, "addFriend: try $nickname $phone")
                        if (nickname != null) {
                            getUserByNickname(nickname)
                        }
                        if (phone != null) {
                            getUserByPhone(phone)
                        }
                    } finally {
//                        delay(500L)
                        Log.e(TAG, "addFriend: finally ${state.friendUser}")
                    }
                }

                job.join()

            Log.e(TAG,"addFriend: I have passed the job")

//                launch {

                    try {
                        Log.e("addFriend", "viewModel ${state.friendUser!!.id}")
                        repository.addFriend(
                            accessToken = accessToken,
                            friendId = state.friendUser!!.id,
                        ).collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    Log.e("addFriend", "result: ${result.data.toString()}")
                                }
                                is Resource.Loading -> {

                                }
                                is Resource.Error -> {
                                    Log.e("addFriend", "error")
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("addFriend", e.toString())
                    }
                }
            }

    private fun addFriendFinally(
        accessToken: String,
    ) {
        Log.e("addFriendFinally", "start")
        viewModelScope.launch {
            try {
                Log.e("addFriend", "viewModel ${state.friendUser!!.id}")
                repository.addFriend(
                    accessToken = accessToken,
                    friendId = state.friendUser!!.id,
                ).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("addFriend", "result: ${result.data.toString()}")
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {
                            Log.e("addFriend", "error")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("addFriend", e.toString())
            }
        }
    }

    private fun getUsers(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false,
        shouldReload: Boolean = true,
    ) {

        viewModelScope.launch {
            repository.getUsers(
                fetchFromRemote = fetchRemote,
                accessToken = accessToken,
                query = query,
                shouldReload = shouldReload
            )
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { users ->
                                state = state.copy(
                                    users = users,
//                                    isLoading = false,
                                )
                            }
                            Log.e("USER VIEW MODEL  GET USERS",state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state =state.copy(
                                isLoading = result.isLoading
                            )
                        }

                    }
                }
        }

    }

    private fun getUsersFindFriend(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false,
        shouldReload: Boolean = true,
    ) {

        viewModelScope.launch {
            repository.getUsersFindFriend(
                fetchFromRemote = fetchRemote,
                accessToken = accessToken,
                query = query,
                shouldReload = shouldReload
            )
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { users ->
                                state = state.copy(
                                    findNewFriendsList = users,
                                )
                            }
                            Log.e("USER VIEW MODEL  GET NEW FRIENDS",state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state =state.copy(
                                isLoading = result.isLoading
                            )
                        }

                    }
                }
        }

    }

    private fun getUser(
        id: String
    ){
        viewModelScope.launch {
            repository.getUser(id = id)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let {
                                user ->
                                state = state.copy(
                                    friendUser = user
                                )
                                onEvent(UserEvent.GetFriendGames(user!!))
                            }
                            Log.e("User View Model Friend",state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    private fun removeSelectedUser(selectedUser: User){
        Log.d("SELECTEDUSER", "REMOVE")
        state = state.copy(
            clickedUsers = state.clickedUsers.toMutableList().filter { user -> user.userId!=selectedUser.userId }.toList(),
//            users = state.users!!.toMutableList().filter { user -> user.userId!=selectedUser.userId }.toList()
        )

        Log.d("SELECTEDUSER", "REMOVE "+state.users!!.size.toString())
    }

    fun insertSelectedUser(selectedUser: User){
        state = state.copy(
            clickedUsers = state.clickedUsers.toMutableList().apply { add(selectedUser) }.toList(),
//            users = state.users!!.toMutableList().apply { add(selectedUser) }.toList()
        )
        Log.d("SELECTEDUSER", "INSERT "+state.users!!.size.toString())
    }

    fun checkSelectedUser(selectedUser: User): Boolean{
        Log.d("SELECTEDUSER", "CHECK")
        var inList = false
        for(user in state.clickedUsers){
            if (user.userId == selectedUser.userId){
                inList = true
                break
            }
        }

        return inList
    }

    fun clearSelectedUser(){
        state = state.copy(
            clickedUsers = listOf(),
        )
    }

    private fun getUsersGames(user: User){
        viewModelScope.launch {
            repository.getUserGames(id = user.id)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let {
                                games ->
                                state = state.copy(
                                    games = games,
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    private fun checkExistsNickname(
        nickname: String,
    ){
        viewModelScope.launch {
            repository.checkExistsNickname(nickname = nickname).collect{
                result ->
                when(result){
                    is Resource.Success -> {
                        Log.e("checkExistsNickname", "viewModel ${result.data.toString()}")
                        state = state.copy(
                            existsUser = result.data!!.exists
                        )
                    }
                    is Resource.Loading -> {
                        if(result.isLoading){
                            state = state.copy(
                                existsUser = null,
                            )
                        }
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun checkExistsPhone(
        phone: String,
    ){
        viewModelScope.launch {
            repository.checkExistsPhone(phone = phone)
                .collect{ result ->
                when(result){
                    is Resource.Success -> {
                        Log.e("checkExistsPhone", "viewModel ${result.data.toString()}")
                        state = state.copy(
                            existsUser = result.data?.exists
                        )
                    }
                    is Resource.Loading -> {
                        if(result.isLoading){
                            state = state.copy(
                                existsUser = null,
                            )
                        }
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    fun setNullForExistsUser(){
        viewModelScope.launch {
            state = state.copy(
                existsUser = null,
            )
        }
    }

}