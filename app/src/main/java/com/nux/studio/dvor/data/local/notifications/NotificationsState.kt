package com.nux.studio.dvor.data.local.notifications

data class NotificationsState(
    val friendInvites: List<NotificationEntity>? = emptyList(),
    val isLoading: Boolean = false,
)
