package com.ledokol.dvor.data.local.notifications

import androidx.room.*

@Dao
interface NotificationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("SELECT * FROM notifications")
    fun getNotifications(): List<NotificationEntity>

    @Query("DELETE FROM notifications")
    fun clearNotifications()

    @Delete
    fun deleteNotification(notification: NotificationEntity)

}