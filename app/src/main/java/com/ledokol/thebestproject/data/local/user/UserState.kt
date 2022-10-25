package com.ledokol.thebestproject.data.local.user

data class UserState(
    var users:List<User>? = mutableListOf(),
    val findNewFriendsList:List<User>? = mutableListOf(), // результат поиска друзей
    val games: List<CurrentApp>? = mutableListOf(),
    val recommendedFriends: MutableList<User>? = mutableListOf(),
    val inviteFriends: MutableList<String> = mutableListOf(),
    val friendUser: User? = null,
    val isLoading: Boolean = false,
    val isLoadingUser: Boolean = false,
    val isLoadingGames: Boolean = false,
    val isRefreshing: Boolean = true,
    val searchQuery: String = "",
    val existsUser: Boolean? = null,
    val openScreen: String? = null,
    val error: String? = null,
//    val numberScreen: Int = 0,
)