package com.ledokol.dvor.data.local.notifications

data class NotificationsState(
    val friendInvites: List<NotificationEntity>? = emptyList(),
    val isLoading: Boolean = false,
)
