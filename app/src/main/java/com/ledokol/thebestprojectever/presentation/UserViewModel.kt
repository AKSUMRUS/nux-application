package com.ledokol.thebestprojectever.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UserEvent
import com.ledokol.thebestprojectever.data.local.user.UserState
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UsersRepository,
): ViewModel() {

    var state by mutableStateOf(UserState())

    val accesToken = ""

    private var searchUser: Job? = null

    init {
        getUsers()
    }

    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.Refresh -> {
                getUsers()
            }
            is UserEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchUser?.cancel()
                searchUser = viewModelScope.launch {
                    delay(500L)
                    getUsers()
                }
            }
            is UserEvent.GetFriendUser -> {
                viewModelScope.launch {
                    getUser(nickname = event.nickname)
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



    private fun getUsers(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false
    ) {

        viewModelScope.launch {
            repository.getUsers(fetchFromRemote = fetchRemote, accesToken = accesToken,query = query)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { users ->
                                state = state.copy(
                                    users = users
                                )
                            }
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
        nickname: String
    ){
        viewModelScope.launch {
            repository.getUser(nickname = nickname)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let {
                                user ->
                                state = state.copy(
                                    friendUser = user
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

}