package com.ledokol.thebestprojectever.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.notifications.NotificationEntity
import com.ledokol.thebestprojectever.data.local.notifications.NotificationsEvent
import com.ledokol.thebestprojectever.data.local.notifications.NotificationsState
import com.ledokol.thebestprojectever.data.repository.NotificationsRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationsRepository
) : ViewModel() {

    var state by mutableStateOf(NotificationsState())


    fun onEvent(event : NotificationsEvent) {
        when (event) {
            is NotificationsEvent.GetFriendsRequests -> {
                getFriendRequestNotifications(token = event.token)
            }
            is NotificationsEvent.AddFriend -> {
                addFriend(event.token, event.notificationEntity)
            }
        }
    }

    private fun clearNotifications(){
        viewModelScope.launch {
            repository.clearNotifications()
        }
    }

    private fun getFriendRequestNotifications(token : String){
        viewModelScope.launch {
            repository.getFriendRequestNotifications(token = token)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                friendInvites = result.data
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }

    private fun addFriend(
        token : String,
        notificationEntity : NotificationEntity
    ) {
        viewModelScope.launch {
            repository.addFriend(token = token, notificationEntity = notificationEntity)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            state = state.copy(
                                friendInvites = result.data
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }

}