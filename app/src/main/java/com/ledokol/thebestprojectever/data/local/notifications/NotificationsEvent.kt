package com.ledokol.thebestprojectever.data.local.notifications

sealed class NotificationsEvent{
    object GetFriendsRequests : NotificationsEvent()
}
