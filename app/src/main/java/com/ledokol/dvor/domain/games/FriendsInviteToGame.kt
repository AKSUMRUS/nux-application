package com.ledokol.dvor.domain.games

data class FriendsInviteToGame(
    val friends_ids: List<String>,
    val app_id: String
)
