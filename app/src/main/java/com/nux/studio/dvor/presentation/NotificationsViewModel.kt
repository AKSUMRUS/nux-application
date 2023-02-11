package com.nux.studio.dvor.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nux.studio.dvor.data.local.notifications.NotificationEntity
import com.nux.studio.dvor.data.local.notifications.NotificationsEvent
import com.nux.studio.dvor.data.local.notifications.NotificationsState
import com.nux.studio.dvor.data.repository.NotificationsRepository
import com.nux.studio.dvor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationsRepository
) : ViewModel() {

    var state by mutableStateOf(NotificationsState())

    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.GetFriendsRequests -> {
                getFriendRequestNotifications(token = event.token)
            }
            is NotificationsEvent.AddFriend -> {
                addFriend(event.notificationEntity)
            }
        }
    }

    private fun clearNotifications() {
        viewModelScope.launch {
            repository.clearNotifications()
        }
    }

    private fun getFriendRequestNotifications(token: String) {
        viewModelScope.launch {
            repository.getFriendRequestNotifications(token = token)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("NotificationsRepository", result.data.toString())
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
        notificationEntity: NotificationEntity
    ) {
        viewModelScope.launch {
            repository.addFriend(notificationEntity = notificationEntity)
                .collect { result ->
                    when (result) {
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