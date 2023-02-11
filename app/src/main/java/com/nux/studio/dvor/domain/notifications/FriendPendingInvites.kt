package com.nux.studio.dvor.domain.notifications

import com.nux.studio.dvor.data.local.user.User

data class FriendPendingInvites(
    val dt_sent: String,
    val from_user: User
)