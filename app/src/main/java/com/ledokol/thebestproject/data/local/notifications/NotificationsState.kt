package com.ledokol.thebestproject.data.local.notifications

data class NotificationsState(
    val friendInvites: List<NotificationEntity>? = emptyList(),
    val isLoading: Boolean = false,
)
