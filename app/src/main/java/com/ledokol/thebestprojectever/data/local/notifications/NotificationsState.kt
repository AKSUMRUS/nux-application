package com.ledokol.thebestprojectever.data.local.notifications

data class NotificationsState(
    val friendInvites: List<NotificationEntity>? = emptyList(),
    val isLoading: Boolean = false,
)
