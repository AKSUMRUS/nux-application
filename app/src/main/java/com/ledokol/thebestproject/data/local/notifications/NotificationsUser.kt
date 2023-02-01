package com.ledokol.thebestproject.data.local.notifications

import com.ledokol.thebestproject.data.local.user.Status

data class NotificationsUser(
    var userId: Int = 0,
    var nickname: String = "",
    var name: String = "",
    var id: String = "",
    var status: Status = Status(),
    var profile_pic: String = "",
    var do_not_disturb: String = "",
)