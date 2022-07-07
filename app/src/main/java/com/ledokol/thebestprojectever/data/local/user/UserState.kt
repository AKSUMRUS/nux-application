package com.ledokol.thebestprojectever.data.local.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData

data class UserState(
    val users:List<User>? = mutableListOf(),
    val clickedUsers:List<User>? = mutableListOf(), // Выбранные пользователи для приглашения в игру
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = true,
    val searchQuery: String = ""
    )