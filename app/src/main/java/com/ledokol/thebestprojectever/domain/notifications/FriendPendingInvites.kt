package com.ledokol.thebestprojectever.domain.notifications

import com.ledokol.thebestprojectever.data.local.user.User

data class FriendPendingInvites(
    val dt_sent : String,
    val from_user : User
)