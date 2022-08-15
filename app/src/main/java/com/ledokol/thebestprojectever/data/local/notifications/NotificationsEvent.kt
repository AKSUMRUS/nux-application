package com.ledokol.thebestprojectever.data.local.notifications

sealed class NotificationsEvent{
    class GetFriendsRequests(val token : String) : NotificationsEvent()
    class AddFriend(val token: String,val notificationEntity: NotificationEntity) : NotificationsEvent()
}
