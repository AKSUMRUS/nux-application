package com.ledokol.thebestprojectever.data.local.user

data class UserState (
    val users:List<User>? = emptyList(),
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
    )