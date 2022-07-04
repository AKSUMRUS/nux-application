package com.ledokol.thebestprojectever.data.local.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class UserState(
    val users:List<User>? = mutableListOf(),
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = true,
    val searchQuery: String = ""
    )