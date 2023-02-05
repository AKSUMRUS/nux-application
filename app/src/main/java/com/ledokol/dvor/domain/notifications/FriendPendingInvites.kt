package com.ledokol.dvor.domain.notifications

import com.ledokol.dvor.data.local.user.User

data class FriendPendingInvites(
    val dt_sent: String,
    val from_user: User
)