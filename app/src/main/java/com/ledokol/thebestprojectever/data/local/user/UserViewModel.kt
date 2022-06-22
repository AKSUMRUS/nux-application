package com.ledokol.thebestprojectever.data.local.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.StockApplication
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDatabase
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UsersRepository
): ViewModel() {

    var state by mutableStateOf(UserState())

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
            repository.getUsers(fetchRemote,query)
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

}