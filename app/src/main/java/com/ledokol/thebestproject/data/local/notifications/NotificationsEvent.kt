package com.ledokol.thebestproject.data.local.notifications

sealed class NotificationsEvent {
    class GetFriendsRequests(val token: String) : NotificationsEvent()
    class AddFriend(val notificationEntity: NotificationEntity) : NotificationsEvent()
}
