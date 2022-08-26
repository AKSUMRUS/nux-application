package com.ledokol.thebestproject.domain.notifications

import com.ledokol.thebestproject.data.local.user.User

data class FriendPendingInvites(
    val dt_sent : String,
    val from_user : User
)