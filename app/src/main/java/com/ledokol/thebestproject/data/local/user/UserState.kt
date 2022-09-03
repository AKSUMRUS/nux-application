package com.ledokol.thebestproject.data.local.user

data class UserState(
    var users:List<User>? = mutableListOf(),
    var findNewFriendsList:List<User>? = mutableListOf(), // результат поиска друзей
    var clickedUsers:List<User> = mutableListOf(), // Выбранные пользователи для приглашения в игру
    var games: List<CurrentApp>? = mutableListOf(),
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isLoadingUser: Boolean = false,
    val isRefreshing: Boolean = true,
    val searchQuery: String = "",
    val existsUser: Boolean? = null,
    val openScreen: String? = null,
    val error: String? = null,
//    val numberScreen: Int = 0,
)