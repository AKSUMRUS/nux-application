package com.ledokol.thebestprojectever.data.local.user

data class UserState(
    var users:List<User>? = mutableListOf(),
    var clickedUsers:List<User> = mutableListOf(), // Выбранные пользователи для приглашения в игру
    var games: List<CurrentApp>? = mutableListOf(),
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = true,
    val searchQuery: String = "",
    val existsUser: Boolean? = null,
//    val numberScreen: Int = 0,
)